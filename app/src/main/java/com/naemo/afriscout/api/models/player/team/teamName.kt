package com.naemo.afriscout.api.models.player.team

import com.google.gson.annotations.SerializedName

data class TeamNameRequest(
    @SerializedName("teamId")
    val teamId: List<Int>?
)

data class TeamNameResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
)

data class Data(
    @SerializedName("team_flags")
    val teamFlags: List<String>,
    @SerializedName("team_names")
    val teamNames: List<String>
)