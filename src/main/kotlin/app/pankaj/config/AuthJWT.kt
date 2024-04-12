package app.pankaj.config

import app.pankaj.utils.ApiResponse
import app.pankaj.utils.Props
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond

const val AUTH_JWT = "auth-jwt"
const val USER_ID_CLAIM_NAME = "userId"
const val RESET_USER_ID_CLAIM_NAME = "resetUserId"

fun Application.configureJwt() {
    install(Authentication) {
        jwt(AUTH_JWT) {
            realm = Props.JWT.realm
            verifier(
                JWT.require(Algorithm.HMAC256(Props.JWT.secret))
                    .withAudience(Props.JWT.audience)
                    .withIssuer(Props.JWT.issuer)
                    .build()
            )
            validate { credential ->
                if (
                    credential.payload.getClaim(USER_ID_CLAIM_NAME).asString() != ""
                    || credential.payload.getClaim(RESET_USER_ID_CLAIM_NAME).asString() != ""
                ) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, ApiResponse(
                    code = HttpStatusCode.Unauthorized.value,
                    message = "Token is not valid or has expired"
                ))
            }
        }
    }
}