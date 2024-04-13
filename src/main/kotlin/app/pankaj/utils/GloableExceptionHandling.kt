package app.pankaj.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import java.time.format.DateTimeParseException


fun Application.configureExceptionHandling() {
   /* install(StatusPages) {
        exception<Throwable> { call, cause ->
            if (cause is InternalException){
                call.respond(HttpStatusCode.InternalServerError, ExceptionResponse(500, cause.message))
            }
            if (cause is IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, ExceptionResponse(HttpStatusCode.BadRequest.value, "Bad request"))
            }
        }
    }*/

    fun Application.module() {
        install(StatusPages) {
            exception<Throwable> { call, cause ->
                val statusCode: HttpStatusCode
                val message: String = when (cause) {
                    is IllegalAccessException -> {
                        statusCode = HttpStatusCode.Unauthorized
                        "Access denied"
                    }
                    is NullPointerException -> {
                        statusCode = HttpStatusCode.BadRequest
                        "Fields are missing"
                    }
                    is IllegalArgumentException -> {
                        statusCode = HttpStatusCode.BadRequest
                        "Bad request"
                    }
                    is DateTimeParseException -> {
                        statusCode = HttpStatusCode.BadRequest
                        "Date not valid"
                    }
                    is AccessDeniedException -> {
                        statusCode = HttpStatusCode.Unauthorized
                        "Requested resource is forbidden"
                    }
                    is NoSuchElementException -> {
                        statusCode = HttpStatusCode.NotFound
                        "Not data present"
                    }
                    else -> {
                        statusCode = HttpStatusCode.InternalServerError
                        "Something went wrong, please try again later"
                    }
                }
                call.respond(statusCode, ExceptionResponse(statusCode.value, message))
            }
        }
    }

}