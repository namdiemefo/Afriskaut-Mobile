package com.naemo.afriskaut.api.models.profile


import com.google.gson.annotations.SerializedName
import com.naemo.afriskaut.db.local.room.profilepicture.ProfilePic

data class ProfileImageResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("statuscode")
    val statuscode: Int?
)

data class Data(
    @SerializedName("date")
    val date: String?,
    @SerializedName("filename")
    val filename: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("__v")
    val v: Int?
)

data class ProfilePicResponse(
    @SerializedName("data")
    val `data`: ProfilePic?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statuscode")
    val statuscode: Int?
)

