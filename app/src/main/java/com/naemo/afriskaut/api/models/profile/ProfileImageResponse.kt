package com.naemo.afriskaut.api.models.profile


import com.google.gson.annotations.SerializedName

data class ProfileImageResponse(
    @SerializedName("data")
    val `data`: ImageData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
)

data class ImageData(
    @SerializedName("filename")
    val filename: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("userId")
    val userId: String
)

