package com.naemo.afriskaut.api.models.player.follow

import com.google.gson.annotations.SerializedName

import com.naemo.afriskaut.db.local.room.following.FollowingData

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

data class FollowData(
    @SerializedName("id")
    // @ColumnInfo(name = "playerId")
    val playerId: String,
    @SerializedName("dob")
    // @ColumnInfo(name = "dob")
    val dob: String,
    @SerializedName("fullname")
    //  @ColumnInfo(name = "fullName")
    val fullname: String,
    @SerializedName("height")
    // @ColumnInfo(name = "height")
    val height: String,
    @SerializedName("image")
    // @ColumnInfo(name = "image")
    val image: String,
    @SerializedName("nationality")
    //  @ColumnInfo(name = "nationality")
    val nationality: String,
    @SerializedName("position")
    //  @ColumnInfo(name = "position")
    val position: String
)

data class FollowingResponse(
    @SerializedName("data")
    val `data`: List<FollowingData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
)

data class UnfollowResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
)



