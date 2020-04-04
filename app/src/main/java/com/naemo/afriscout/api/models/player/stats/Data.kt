package com.naemo.afriscout.api.models.player.stats


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("_id")
    val id: String,
    @SerializedName("player_id")
    val playerId: Int,
    @SerializedName("stats")
    val stats: List<Stat>
)