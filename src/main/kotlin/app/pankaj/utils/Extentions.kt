package app.pankaj.utils

import io.ktor.server.application.ApplicationCall
import org.jetbrains.exposed.sql.SizedIterable
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Base64


fun <T> SizedIterable<T>.notEmpty() = !empty()

fun ByteArray.toBase64(): String =
    String(Base64.getEncoder().encode(this))

fun Instant.toIsoString(): String {
    val dateFormatter = DateTimeFormatter.ISO_INSTANT
    return dateFormatter.format(this)
}

fun String.toInstant(): Instant {
    return Instant.parse(this)
}

fun ApplicationCall.getProperty(name: String) =
    application.environment.config.property(name).getString()

inline fun String?.ifNullOrEmpty(defaultValue: () -> String): String =
    if (this?.isEmpty() != false) defaultValue() else this
