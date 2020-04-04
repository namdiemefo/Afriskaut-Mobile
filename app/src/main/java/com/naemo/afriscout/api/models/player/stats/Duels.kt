package com.naemo.afriscout.api.models.player.stats


import com.google.gson.annotations.SerializedName

data class Duels(
    @SerializedName("total")
    val total: Any,
    @SerializedName("won")
    val won: Any
)