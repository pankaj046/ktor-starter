package app.pankaj.api.auth

import app.pankaj.api.auth.domain.model.Role
import app.pankaj.config.noData
import app.pankaj.methods.hmGet
import app.pankaj.utils.ApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(){

    hmGet(path = "/user", acceptedRoles = hashSetOf(Role.USER, Role.ADMIN),
        validateRequest = {call.noData()}){
        call.respond(HttpStatusCode.OK, ApiResponse(
            code = HttpStatusCode.OK.value,
            message = "Success",
            data = this.user
        ))
    }

}