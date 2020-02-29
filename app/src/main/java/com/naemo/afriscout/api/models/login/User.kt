package com.naemo.afriscout.api.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("__v")
    val __v: Int,
    @Expose
    @SerializedName("_id")
    val _id: String = "",
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("firstName")
    val firstName: String,
    @Expose
    @SerializedName("isVerified")
    val isVerified: Boolean,
    @Expose
    @SerializedName("jwt_token")
    val jwt_token: String,
    @Expose
    @SerializedName("lastName")
    val lastName: String,
    @Expose
    @SerializedName("password")
    val password: String,
    @Expose
    @SerializedName("resetPasswordExpires")
    val resetPasswordExpires: Any,
    @Expose
    @SerializedName("resetPasswordToken")
    val resetPasswordToken: String,
    @Expose
    @SerializedName("role")
    val role: String,
    @Expose
    @SerializedName("status")
    val status: String,
    @Expose
    @SerializedName("subscription")
    val subscription: String
) {

    constructor(email: String?, firstName: String?, isVerified: Boolean?, jwt_token: String?, lastName: String?, role: String?, status: String?, subscription: String?): this(0,
        "id", "time", email.toString(), firstName.toString(), isVerified!!, jwt_token.toString(), lastName.toString(), "password", "reset", "resetToken", role.toString(), status.toString(), subscription.toString())
}