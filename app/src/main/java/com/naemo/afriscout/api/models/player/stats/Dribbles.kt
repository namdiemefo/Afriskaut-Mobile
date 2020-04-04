package com.naemo.afriscout.api.models.player.stats


import com.google.gson.annotations.SerializedName

data class Dribbles(
    @SerializedName("attempts")
    val attempts: Any,
    @SerializedName("dribbled_past")
    val dribbledPast: Any,
    @SerializedName("success")
    val success: Any
)