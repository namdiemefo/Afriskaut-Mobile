package com.naemo.afriscout.api.models.profile


import com.google.gson.annotations.SerializedName

data class RetrieveImageResponse(
    @SerializedName("data")
    val data: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
)
