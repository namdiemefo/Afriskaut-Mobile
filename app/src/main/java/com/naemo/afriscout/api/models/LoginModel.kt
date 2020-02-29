package com.naemo.afriscout.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.naemo.afriscout.api.models.login.Data

data class LoginRequest(
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @Expose
    @SerializedName("data")
    val data: Data,
    @Expose
    @SerializedName("message")
    val message: String,
    @Expose
    @SerializedName("statuscode")
    val statuscode: Int
)