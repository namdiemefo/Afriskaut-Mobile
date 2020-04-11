package com.naemo.afriscout.api.models.player.team


import com.google.gson.annotations.SerializedName

data class TeamNameX(
    @SerializedName("data")
    val `data`:  Map<Int, String>?,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
)