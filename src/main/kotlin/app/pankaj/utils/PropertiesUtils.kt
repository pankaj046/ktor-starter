package app.pankaj.utils

import app.pankaj.utils.Environment.*

enum class Environment {
    PROD, DEV, LOCAl
}

val environment = try {
    valueOf(env("ENVIRONMENT"))
} catch (e: Exception) {
    LOCAl
}

fun <T> prop(local: T, server: T, testServer: T? = null) = when (environment) {
    DEV -> testServer ?: server
    PROD -> server
    LOCAl -> local
}

fun env(name: String) = System.getenv(name) ?: ""
