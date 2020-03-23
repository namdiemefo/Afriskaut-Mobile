package com.naemo.afriscout.api.models.player.follow

import com.google.gson.annotations.SerializedName

data class FollowResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int,
    @SerializedName("userId")
    val userId: String
)

data class Data(
    @SerializedName("dob")
    val dob: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("position")
    val position: String
)