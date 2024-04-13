package app.pankaj.config

import app.pankaj.api.auth.userRoute
import app.pankaj.route.authRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/api/v1") {
            authRouting()
            userRoute()
        }
    }
}
