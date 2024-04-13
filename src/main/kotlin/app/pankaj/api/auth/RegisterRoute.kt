package app.pankaj.api.auth

import app.pankaj.api.auth.domain.UserRepository
import app.pankaj.api.auth.domain.model.Role
import app.pankaj.dao.asUser
import app.pankaj.methods.hmPost
import app.pankaj.utils.ApiResponse
import app.pankaj.utils.TokenUtils
import app.pankaj.utils.TokenUtils.generatePassword
import io.ktor.http.*
import io.ktor.server.application.*
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
            call.respond(HttpStatusCode.BadRequest, ApiResponse<Unit>(
                code = HttpStatusCode.BadRequest.value,
                message = "User already exists"
            ))
        }else{
            userRepository.registerUser(it)
            call.respond(HttpStatusCode.OK, ApiResponse<Unit>(
                code = HttpStatusCode.OK.value,
                message = "User registered successfully"
            ))
        }
    }


    hmPost (path = "/login", acceptedRoles = hashSetOf(Role.Public), validateRequest = {
        call.isLoginIsValidRequest()
    }){
        val user = userRepository.findUserByEmail(it.email)
        if (user!=null){
            if (user.isDeleted || !user.isActive) {
                /**
                 * send this response when user is not active or account
                 * consider as deleted
                 * */
                call.respond(HttpStatusCode.BadRequest, ApiResponse<Unit>(
                    code = HttpStatusCode.BadRequest.value,
                    message = "Invalid login credentials"
                ))
            }

            val hashedPassword = generatePassword(it.password, user.salt)
            if (user.password != hashedPassword) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse<Unit>(
                    code = HttpStatusCode.BadRequest.value,
                    message = "Invalid login credentials"
                ))
            }

            call.respond(HttpStatusCode.OK, ApiResponse(
                code = HttpStatusCode.OK.value,
                message = "Login",
                data = user.asUser().toLoginUser(
                    token = TokenUtils.createToken(user.email, user.fullName),
                    refreshToken =  TokenUtils.refreshToken(user.id.value)
                )
            ))
        }else{
            call.respond(HttpStatusCode.BadRequest, ApiResponse<Unit>(
                code = HttpStatusCode.BadRequest.value,
                message = "Invalid login credentials"
            ))
        }
    }
}