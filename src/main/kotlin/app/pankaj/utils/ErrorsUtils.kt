package app.pankaj.utils

import io.ktor.http.*
import io.ktor.util.*

class ErrorResponse(val code: Int, message: String = "", data: Any? = null) : Throwable(message)

@KtorDsl
fun respondError(status: HttpStatusCode, message: String = ""): Nothing = throw ErrorResponse(status.value, message)