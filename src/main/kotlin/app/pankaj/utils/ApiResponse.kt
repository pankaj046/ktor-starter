package app.pankaj.utils

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    var code:Int?=null,
    var message:String?=null,
    @Contextual
    var data:Any?=null,
)


@Serializable
data class ExceptionResponse(
    var code:Int?=null,
    var message:String?=null,
    var data:Int?=null,
)