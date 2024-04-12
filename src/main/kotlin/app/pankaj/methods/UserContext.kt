package app.pankaj.methods

import app.pankaj.config.USER_ID_CLAIM_NAME
import app.pankaj.dao.UserDao
import app.pankaj.dao.UsersTable
import app.pankaj.dao.asUser
import app.pankaj.api.auth.domain.model.Role
import app.pankaj.api.auth.domain.model.User
import app.pankaj.utils.Param
import app.pankaj.utils.QueryParam
import com.auth0.jwt.interfaces.Payload
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.util.pipeline.PipelineContext
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.not
import org.jetbrains.exposed.sql.transactions.transaction
import app.pankaj.utils.respondError

/**
 * A context object for user-related information.
 *
 * @property call the Ktor application call
 * @property user the authenticated user
 */
class UserContext(
    val call: ApplicationCall,
    val user: User,
) {
    /**
     * Retrieves the value of the request parameter associated with this instance of [Param] and converts it to the
     * specified type, or returns null if the conversion fails.
     *
     * @return the parameter value, or null if the conversion fails
     */
    inline fun <reified T : Any> Param.receiveOrNull(): T? {
        val value =
            if (this is QueryParam)
                call.request.queryParameters[toString()]
            else
                call.parameters[toString()]

        return value.toTypeOrNull()
    }

    /**
     * Retrieves the value of the request parameter associated with this instance of [Param] and converts it to the
     * specified type, or returns an error response if the conversion fails.
     *
     * @return the parameter value, or an error response if the conversion fails
     */
    inline fun <reified T : Any> Param.receiveOrError() = receiveOrNull<T>() ?: respondError(
        HttpStatusCode.BadRequest,
        "Cannot convert parameter \"${toString()}\" to ${T::class.simpleName}"
    )

    val payload: Payload?
        get() = call.principal<JWTPrincipal>()?.payload
}

/**
 * Retrieves the authenticated user from the Ktor pipeline context, or returns a default public user if no user is authenticated.
 *
 * @return the authenticated user, or a default public user if no user is authenticated
 */
fun PipelineContext<Unit, ApplicationCall>.getUser(): User {
    val userId: Int? =
        call.principal<JWTPrincipal>()?.payload?.claims?.get(USER_ID_CLAIM_NAME)?.asInt()

    return userId?.let { id ->
        transaction {
            UserDao.find { UsersTable.id eq id and not(UsersTable.isDeleted) and not(UsersTable.isActive) }.firstOrNull()?.asUser()
        }
    } ?: User(id = -1, email = "", fullName = "", role = Role.Public)
}
