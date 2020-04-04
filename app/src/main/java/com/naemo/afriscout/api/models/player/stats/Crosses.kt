package com.naemo.afriscout.api.models.player.stats


import com.google.gson.annotations.SerializedName

data class Crosses(
    @SerializedName("accurate")
    val accurate: Any,
    @SerializedName("total")
    val total: Any
)