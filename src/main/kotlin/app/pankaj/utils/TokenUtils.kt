package app.pankaj.utils

import app.pankaj.config.OAuthEmailClaim
import app.pankaj.config.OAuthFullNameClaim
import app.pankaj.config.USER_ID_CLAIM_NAME
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

object TokenUtils {

    fun refreshToken(id: Int): String {
        return JWT.create().withAudience(Props.JWT.audience).withIssuer(Props.JWT.issuer)
            .withClaim(USER_ID_CLAIM_NAME, id)
            .withExpiresAt(Date(System.currentTimeMillis() + 30.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(Props.JWT.secret))
    }

    fun createOAuthToken(email: String, fullName: String): String {
        return JWT.create()
            .withAudience(Props.JWT.audience)
            .withIssuer(Props.JWT.issuer)
            .withClaim(OAuthEmailClaim, email)
            .withClaim(OAuthFullNameClaim, fullName)
            .withExpiresAt(Date(System.currentTimeMillis() + 10.minutes.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(Props.JWT.secret))
    }

    fun generatePassword(password: String, salt: String): String {
        return (password + salt).toSHA256()
    }
}