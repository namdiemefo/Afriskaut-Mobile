package com.naemo.afriskaut.api.models.suggestion

import com.google.gson.annotations.SerializedName
import com.naemo.afriskaut.db.local.room.suggest.SuggestData

data class SuggestionResponse(
    @SerializedName("data")
    val `data`: List<SuggestData>?,
    @SerializedName("statuscode")
    val statuscode: Int?
)

data class SuggestionRequest(
    val under: String
)

