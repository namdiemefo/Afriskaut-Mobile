package com.naemo.afriscout.api.models.search


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @Expose
    @SerializedName("query")
    val query: String
)