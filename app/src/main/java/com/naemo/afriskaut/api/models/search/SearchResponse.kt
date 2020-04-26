package com.naemo.afriskaut.api.models.search

import com.google.gson.annotations.SerializedName
import com.naemo.afriskaut.db.local.room.search.Data

data class SearchResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("statuscode")
    val statuscode: Int
)