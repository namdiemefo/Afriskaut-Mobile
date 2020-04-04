package com.naemo.afriscout.api.models.player.stats


import com.google.gson.annotations.SerializedName

data class Passes(
    @SerializedName("accuracy")
    val accuracy: Any,
    @SerializedName("key_passes")
    val keyPasses: Any,
    @SerializedName("total")
    val total: Any
)