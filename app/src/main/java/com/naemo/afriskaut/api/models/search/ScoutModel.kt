package com.naemo.afriskaut.api.models.search

import com.google.gson.annotations.SerializedName
import com.naemo.afriskaut.db.local.room.search.Player

data class SearchPlayerRequest(
    val field: String,
    val keyword: String
)

data class SearchPlayerResponse(
    @SerializedName("players")
    val players: List<Player>,
    @SerializedName("statuscode")
    val statuscode: Int?
)

data class ScoutPlayerRequest(
    val nationality: String?,
    val position: String?,
    val age: String?
)

data class ScoutPlayerResponse(
    @SerializedName("players")
    val players: List<com.naemo.afriskaut.db.local.room.scout.Player>,
    @SerializedName("statuscode")
    val statuscode: Int
)