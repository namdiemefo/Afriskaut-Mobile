package com.naemo.afriscout.api.models.player.stats

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.naemo.afriscout.db.local.room.stats.Stats

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