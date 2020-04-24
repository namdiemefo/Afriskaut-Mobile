package com.naemo.afriscout.api.models.player.league


import com.google.gson.annotations.SerializedName
import com.naemo.afriscout.api.models.player.league.Data


data class LeagueNameRequest(
    @SerializedName("leagueId")
    val leagueId: List<Int>?
)

data class LeagueNameResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
)

data class Data(
    @SerializedName("leagueLogo")
    val leagueLogo: List<String>,
    @SerializedName("leagueName")
    val leagueName: List<String>
)