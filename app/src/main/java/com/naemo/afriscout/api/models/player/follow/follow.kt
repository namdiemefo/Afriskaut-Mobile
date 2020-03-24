package com.naemo.afriscout.api.models.player.follow

import com.google.gson.annotations.SerializedName
import com.naemo.afriscout.db.local.room.follow.FollowData

data class FollowResponse(
    @SerializedName("data")
    val `data`: FollowData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int,
    @SerializedName("userId")
    val userId: String
)

