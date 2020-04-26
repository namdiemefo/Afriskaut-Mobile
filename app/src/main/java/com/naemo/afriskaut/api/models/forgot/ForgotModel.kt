package com.naemo.afriskaut.api.models.forgot


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForgotResponse(
    @SerializedName("response")
    val response: Response
)

data class Response(
    @SerializedName("message")
    val message: String,
    @SerializedName("reset_token")
    val resetToken: String,
    @SerializedName("statuscode")
    val statuscode: Int
)

data class ForgotRequest(
    @Expose
    @SerializedName("email")
    val email: String
)