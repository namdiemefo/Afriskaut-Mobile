package com.naemo.afriskaut.api.models.report

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DeleteReportRequest(
    val reportId: String
)

data class CreateReportRequest(
    val ballControl: Int,
    val composure: Int,
    val decisionMaking: Int,
    val finishing: Int,
    val firstTouch: Int,
    val heading: Int,
    val longPass: Int,
    val matchName: String?,
    val playerId: String?,
    val positioning: Int?,
    val report: String?,
    val shortPass: Int,
    val shotStopping: Int,
    val technique: Int,
    val pressing: Int
)

data class CreateReportResponse(
    @SerializedName("data")
    val `data`: List<Any>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statuscode")
    val statuscode: Int?
)

data class ViewReportRequest(
    val playerId: String
)

data class ViewReportResponse(
    @SerializedName("data")
    val `data`: List<ReportData>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statuscode")
    val statuscode: Int?
)

data class ReportData(
    @SerializedName("ballcontrol")
    val ballcontrol: Int?,
    @SerializedName("composure")
    val composure: Int?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("decisionmaking")
    val decisionmaking: Int?,
    @SerializedName("finishing")
    val finishing: Int?,
    @SerializedName("firsttouch")
    val firsttouch: Int?,
    @SerializedName("heading")
    val heading: Int?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("longpass")
    val longpass: Int?,
    @SerializedName("matchname")
    val matchname: String?,
    @SerializedName("playerid")
    val playerid: String?,
    @SerializedName("positioning")
    val positioning: Int,
    @SerializedName("pressing")
    val pressing: Int?,
    @SerializedName("report")
    val report: String?,
    @SerializedName("scoutid")
    val scoutid: String?,
    @SerializedName("shortpass")
    val shortpass: Int?,
    @SerializedName("shotstopping")
    val shotstopping: Int?,
    @SerializedName("technique")
    val technique: Int?,
    @SerializedName("__v")
    val v: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString().toString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(ballcontrol)
        parcel.writeValue(composure)
        parcel.writeString(createdAt)
        parcel.writeValue(decisionmaking)
        parcel.writeValue(finishing)
        parcel.writeValue(firsttouch)
        parcel.writeValue(heading)
        parcel.writeString(id)
        parcel.writeValue(longpass)
        parcel.writeString(matchname)
        parcel.writeString(playerid)
        parcel.writeValue(positioning)
        parcel.writeValue(pressing)
        parcel.writeString(report)
        parcel.writeString(scoutid)
        parcel.writeValue(shortpass)
        parcel.writeValue(shotstopping)
        parcel.writeValue(technique)
        parcel.writeValue(v)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReportData> {
        override fun createFromParcel(parcel: Parcel): ReportData {
            return ReportData(parcel)
        }

        override fun newArray(size: Int): Array<ReportData?> {
            return arrayOfNulls(size)
        }
    }
}
