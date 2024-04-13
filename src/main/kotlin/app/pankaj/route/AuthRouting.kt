package app.pankaj.route

import app.pankaj.api.auth.register
import io.ktor.server.routing.*


fun Route.authRouting(){
    route("/auth") {
        register()
    }
}