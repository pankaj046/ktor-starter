package app.pankaj.api.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val email: String,
    val fullName: String,
    val role: Role
){

    fun toLoginUser(token: String, refreshToken: String): LoginUser {
        return LoginUser(
            id = id,
            email = email,
            fullName = fullName,
            role = role,
            token = token,
            refreshToken = token
        )
    }
}



@Serializable
data class LoginUser(
    val id: Int,
    val email: String,
    val fullName: String,
    val role: Role,
    val token: String,
    val refreshToken: String,
)
