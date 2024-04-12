package app.pankaj.utils

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    var code:Int?=null,
    var message:String?=null,
    var data:Int?=null,
)
