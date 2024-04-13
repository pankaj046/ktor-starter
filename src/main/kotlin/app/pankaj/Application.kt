package app.pankaj

import app.pankaj.config.configureJwt
import app.pankaj.database.configureDatabases
import app.pankaj.database.configureTables
import app.pankaj.config.configureRouting
import app.pankaj.config.configureSecurity
import app.pankaj.config.configureSerialization
import app.pankaj.di.serverModule
import app.pankaj.utils.ApiResponse
import app.pankaj.utils.ExceptionResponse
import app.pankaj.utils.Props
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.koin.ktor.plugin.Koin
import org.slf4j.LoggerFactory
import java.util.*

val Logger = LoggerFactory.getLogger("pankaj.app")


fun main() {
    try {
        val environment = applicationEngineEnvironment {
            log = LoggerFactory.getLogger("ktor.application")
            connector {
                port = Props.Ktor.port
            }
            module(Application::module)
        }
        embeddedServer(Netty, environment).start(wait = true)
    } catch (e: Exception) {
        Logger.error(e.message)
    }
}

fun Application.module() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

    install(Koin) {
        val application = this@module
        modules(serverModule, org.koin.dsl.module { single<Application> { application } })
    }


    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ExceptionResponse(500, cause.message))
        }
    }

    install(CORS) {
        HttpMethod.DefaultMethods.forEach { allowMethod(it) }
        allowHeader(HttpHeaders.AccessControlAllowHeaders)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.Cookie)
        allowHeader(HttpHeaders.Authorization)
        allowCredentials = true
        maxAgeInSeconds = 1
        anyHost()
    }

    configureSerialization()
    configureDatabases()
    configureTables()
    configureSecurity()
    configureJwt()
    configureRouting()
}
