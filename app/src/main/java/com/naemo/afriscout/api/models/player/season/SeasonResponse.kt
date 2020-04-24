package com.naemo.afriscout.api.models.player.season


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SeasonNameRequest(
    @Expose
    @SerializedName("seasonId")
    val seasonId: List<Int>?
)

data class SeasonNameResponse(
    @SerializedName("data")
    val `data`: List<String>,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
)