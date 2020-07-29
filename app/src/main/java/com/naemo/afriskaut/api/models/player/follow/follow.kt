package com.naemo.afriskaut.api.models.player.follow

import com.google.gson.annotations.SerializedName

import com.naemo.afriskaut.db.local.room.following.FollowingData
import com.naemo.afriskaut.db.local.room.search.Player

data class FollowRequest(
    val playerId: String
)

data class UnfollowRequest(
    val playerId: String
)

data class FollowResponse(
    @SerializedName("data")
    val `data`: List<Any>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statuscode")
    val statuscode: Int?
)

data class UnFollowResponse(
    @SerializedName("data")
    val `data`: List<Any>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statuscode")
    val statuscode: Int?
)

data class FollowingResponse(
    @SerializedName("data")
    val `data`: List<FollowingData>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statuscode")
    val statuscode: Int?
)








