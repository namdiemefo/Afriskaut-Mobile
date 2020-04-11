package com.naemo.afriscout.api.models.player.team

import com.google.gson.annotations.SerializedName

data class TeamNameRequest(
    @SerializedName("teamId")
    val teamId: ArrayList<Int>?
)

data class TeamNameResponse(
    @SerializedName("data")
    val `data`: List<String>,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
)