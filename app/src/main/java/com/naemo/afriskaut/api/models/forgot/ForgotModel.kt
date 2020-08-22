package com.naemo.afriskaut.api.models.forgot


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForgotResponse(
    @SerializedName("statuscode")
    val statuscode: Int,
    @SerializedName("message")
    val message: String

)


data class ForgotRequest(
    @Expose
    @SerializedName("email")
    val email: String
)

//data class ChangePasswordResponse(
//    @SerializedName("response")
//    val response: ChangeResponse?
//)
//
//data class ChangeResponse(
//    @SerializedName("message")
//    val message: String?,
//    @SerializedName("statuscode")
//    val statuscode: Int?
//)
//
//data class ChangePasswordRequest(
//    val email: String?
//
//)