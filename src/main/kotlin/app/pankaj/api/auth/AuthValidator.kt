package app.pankaj.api.auth

import app.pankaj.api.auth.domain.model.Register


import java.util.regex.Pattern

fun isRegisterIsValidRequest(request: Register?): String {
    var error = ""
    if (request == null) {
        error = "Invalid or missing request body."
        return error
    }
    if (request.email.isEmpty()) {
        error = "Email cannot be empty."
        return error
    }
    if (!isEmailValid(request.email)) {
        error = "Invalid email format."
        return error
    }
    if (request.password.length < 8) {
        error = "Password must be at least 8 characters long."
        return error
    }
    if (!isPasswordStrong(request.password)) {
        error = "Password must contain at least one uppercase letter, one lowercase letter, and one number."
        return error
    }
    return error
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
