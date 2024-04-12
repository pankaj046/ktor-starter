package app.pankaj.api.auth

import app.pankaj.api.auth.domain.model.Register


fun isRegisterIsValidRequest(request: Register?): String {
    var error = ""
    if (request == null) {
        error = "Invalid or missing request body."
        return error
    }
    if (request.email.isEmpty()){
        error = "Email cannot be empty."
        return error
    }
    return error
}