package app.pankaj.api.auth

import app.pankaj.api.auth.domain.UserRepository
import app.pankaj.api.auth.domain.model.Register
import app.pankaj.api.auth.domain.model.Role
import app.pankaj.methods.hmPost
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.register(userRepository: UserRepository) {

    hmPost (path = "/register", acceptedRoles = hashSetOf(Role.Public), validateRequest = {
        isRegisterIsValidRequest(kotlin.runCatching { call.receiveNullable<Register>() }.getOrNull())
    }){

    }
}