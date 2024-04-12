package app.pankaj.methods

import app.pankaj.config.AUTH_JWT
import app.pankaj.api.auth.domain.model.Role
import app.pankaj.utils.ApiResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.util.KtorDsl
import app.pankaj.utils.respondError
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*


@KtorDsl
fun Route.hmPost(
    acceptedRoles: Set<Role> = setOf(Role.Public),
    path: String = "",
    validateRequest: suspend PipelineContext<Unit, ApplicationCall>.() -> String,
    body: suspend UserContext.() -> Unit
) {
    authenticate(AUTH_JWT, optional = acceptedRoles.contains(Role.Public)) {
        post(path) ktorPost@{
            val user = getUser()
            if (!acceptedRoles.contains(Role.Public) && !acceptedRoles.contains(user.role)) {
                respondError(HttpStatusCode.Forbidden)
            }
            val message = validateRequest()
            if (message.isNotEmpty()) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse(
                    code = HttpStatusCode.BadRequest.value,
                    message = message
                ))
                return@ktorPost
            }
            body.invoke(UserContext(call, user))
        }
    }
}
