package app.pankaj.methods

import app.pankaj.config.AUTH_JWT
import app.pankaj.api.auth.domain.model.Role
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.patch
import io.ktor.util.KtorDsl
import app.pankaj.utils.respondError

@KtorDsl
fun Route.hmPatch(
    acceptedRoles: Set<Role> = setOf(Role.Public),
    path: String = "",
    body: suspend UserContext.() -> Unit,
) {
    authenticate(AUTH_JWT, optional = acceptedRoles.contains(Role.Public)) {
        patch(path) ktorPatch@{
            val user = getUser()
            if (!acceptedRoles.contains(Role.Public) && !acceptedRoles.contains(user.role)) {
                respondError(HttpStatusCode.Forbidden)
            }
            body.invoke(UserContext(call, user))
        }
    }
}
