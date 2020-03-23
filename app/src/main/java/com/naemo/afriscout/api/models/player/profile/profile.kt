package com.naemo.afriscout.api.models.player.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("statuscode")
    val statuscode: Int
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
    val position: String,
    @SerializedName("team")
    val team: String
)

data class ProfileRequest(
    @Expose
    @SerializedName("playerId")
    val playerId: String
)