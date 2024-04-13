package app.pankaj.utils

import app.pankaj.config.OAuthEmailClaim
import app.pankaj.config.OAuthFullNameClaim
import app.pankaj.config.USER_ID_CLAIM_NAME
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

object TokenUtils {

    private val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(Props.JWT.secret))
        .withIssuer(Props.JWT.issuer)
        .build()

    fun refreshToken(id: Int): String {
        return JWT.create().withAudience(Props.JWT.audience).withIssuer(Props.JWT.issuer)
            .withClaim(USER_ID_CLAIM_NAME, id)
            .withExpiresAt(Date(System.currentTimeMillis() + 30.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(Props.JWT.secret))
    }

    fun createToken(email: String, fullName: String): String {
        return JWT.create()
            .withAudience(Props.JWT.audience)
            .withIssuer(Props.JWT.issuer)
            .withClaim(OAuthEmailClaim, email)
            .withClaim(OAuthFullNameClaim, fullName)
            .withExpiresAt(Date(System.currentTimeMillis() + 10.minutes.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(Props.JWT.secret))
    }


    fun validateToken(token: String): Boolean {
        try {
            val decodedJWT = verifier.verify(token)
            return !decodedJWT.expiresAt.before(Date())
        } catch (e: Exception) {
            return false
        }
    }

    fun generatePassword(password: String, salt: String): String {
        return (password + salt).toSHA256()
    }
}