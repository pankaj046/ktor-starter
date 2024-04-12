package app.pankaj.api.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Register(
    val email: String,
    val fullName: String,
    val password: String,
)
