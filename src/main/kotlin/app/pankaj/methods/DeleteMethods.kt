package app.pankaj.methods

import app.pankaj.config.AUTH_JWT
import app.pankaj.api.auth.domain.model.Role
import app.pankaj.utils.ApiResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.util.KtorDsl
import app.pankaj.utils.respondError
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

@KtorDsl
fun <T> Route.hmDelete(
    acceptedRoles: Set<Role> = setOf(Role.Public),
    path: String = "",
    validateRequest: suspend PipelineContext<Unit, ApplicationCall>.() -> Pair<String, T?>,
    body: suspend UserContext.(T) -> Unit
) {
    authenticate(AUTH_JWT, optional = acceptedRoles.contains(Role.Public)) {
        delete(path) ktorPost@{
            val user = getUser()
            if (!acceptedRoles.contains(Role.Public) && !acceptedRoles.contains(user.role)) {
                respondError(HttpStatusCode.Forbidden)
            }
            val (message, data) = validateRequest()
            if (message.isNotEmpty()) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse<Unit>(
                    code = HttpStatusCode.BadRequest.value,
                    message = message
                ))
                return@ktorPost
            }
            data?.let { body.invoke(UserContext(call, user), it) }
        }
    }
}
