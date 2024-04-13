package app.pankaj.utils

import app.pankaj.config.USER_ID_CLAIM_NAME
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*
import kotlin.time.Duration.Companion.days

object TokenUtils {


    fun createToken(id: Int): String {
        val currentTimeMillis = System.currentTimeMillis()
        return JWT.create()
            .withAudience(Props.JWT.audience)
            .withIssuer(Props.JWT.issuer)
            .withClaim(USER_ID_CLAIM_NAME, id)
            .withNotBefore(Date(currentTimeMillis))
            .withIssuedAt(Date(currentTimeMillis))
            .withExpiresAt(Date(System.currentTimeMillis() + 7.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(Props.JWT.secret))
    }

    fun refreshToken(id: Int): String {
        return JWT.create()
            .withAudience(Props.JWT.audience)
            .withIssuer(Props.JWT.issuer)
            .withClaim(USER_ID_CLAIM_NAME, id)
            .withExpiresAt(Date(System.currentTimeMillis() + 7.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(Props.JWT.secret))
    }

    fun generatePassword(password: String, salt: String): String {
        return (password + salt).toSHA256()
    }
}