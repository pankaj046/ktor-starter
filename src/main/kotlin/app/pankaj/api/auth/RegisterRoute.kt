package app.pankaj.api.auth

import app.pankaj.api.auth.domain.UserRepository
import app.pankaj.api.auth.domain.model.Register
import app.pankaj.api.auth.domain.model.Role
import app.pankaj.methods.hmPost
import app.pankaj.utils.ApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.register() {

    val userRepository: UserRepository by inject()

    hmPost (path = "/register", acceptedRoles = hashSetOf(Role.Public), validateRequest = {
        call.isRegisterIsValidRequest()
    }){
        val user = userRepository.findUserByEmail(it.email)
        if (user!=null){
            call.respond(HttpStatusCode.BadRequest, ApiResponse(
                code = HttpStatusCode.BadRequest.value,
                message = "User already exists"
            ))
        }else{
            userRepository.registerUser(it)
            call.respond(HttpStatusCode.OK, ApiResponse(
                code = HttpStatusCode.OK.value,
                message = "User registered successfully"
            ))
        }
    }
}