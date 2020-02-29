package com.naemo.afriscout.api.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data(
    @Expose
    @SerializedName("user")
    val user: User
)