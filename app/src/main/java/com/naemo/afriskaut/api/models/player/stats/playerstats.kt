package com.naemo.afriskaut.api.models.player.stats

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.naemo.afriskaut.db.local.room.stats.Stats

data class PlayerStatsResponse(
    @SerializedName("data")
    val `data`: Stats,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
)

data class PlayerStatsRequest(
    @Expose
    @SerializedName("playerId")
    val playerId: Int
)