package com.naemo.afriscout.api.models.player.stats


import com.google.gson.annotations.SerializedName

data class Fouls(
    @SerializedName("committed")
    val committed: Any,
    @SerializedName("drawn")
    val drawn: Any
)