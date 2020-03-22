package com.naemo.afriscout.api.models.search

import com.google.gson.annotations.SerializedName
import com.naemo.afriscout.db.local.room.search.Data

data class SearchResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("statuscode")
    val statuscode: Int
)