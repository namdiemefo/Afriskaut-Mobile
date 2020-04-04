package com.naemo.afriscout.api.models.player.stats


import com.google.gson.annotations.SerializedName

data class Penalties(
    @SerializedName("committed")
    val committed: Any,
    @SerializedName("missed")
    val missed: Any,
    @SerializedName("saves")
    val saves: Any,
    @SerializedName("scores")
    val scores: Any,
    @SerializedName("won")
    val won: Any
)