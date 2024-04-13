package app.pankaj.api.auth

import app.pankaj.api.auth.domain.model.Register
import io.ktor.server.application.*
import io.ktor.server.request.*


import java.util.regex.Pattern

suspend fun ApplicationCall.isRegisterIsValidRequest(): Pair<String, Register?> {
    val request = this.receiveNullable<Register>()
    var error = ""
    if (request == null) {
        error = "Invalid or missing request body."
        return Pair(error, null)
    }
    if (request.email.isEmpty()) {
        error = "Email cannot be empty."
        return Pair(error, null)
    }
    if (!isEmailValid(request.email)) {
        error = "Invalid email format."
        return Pair(error, null)
    }
    if (request.password.length < 8) {
        error = "Password must be at least 8 characters long."
        return Pair(error, null)
    }
    if (!isPasswordStrong(request.password)) {
        error = "Password must contain at least one uppercase letter, one lowercase letter, and one number."
        return Pair(error, null)
    }
    return Pair(error, request)
}

fun isEmailValid(email: String): Boolean {
    val emailRegex = ("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}\$")
    val pattern = Pattern.compile(emailRegex)
    return pattern.matcher(email).matches()
}

fun isPasswordStrong(password: String): Boolean {
    val uppercasePattern = Pattern.compile("[A-Z]")
    val lowercasePattern = Pattern.compile("[a-z]")
    val digitPattern = Pattern.compile("[0-9]")

    val hasUppercase = uppercasePattern.matcher(password).find()
    val hasLowercase = lowercasePattern.matcher(password).find()
    val hasDigit = digitPattern.matcher(password).find()

    return hasUppercase && hasLowercase && hasDigit
}
