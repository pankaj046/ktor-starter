package app.pankaj.api.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val email: String,
    val fullName: String,
    val role: Role
)
