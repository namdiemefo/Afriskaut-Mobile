package com.naemo.afriskaut.db.local.room.search

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.naemo.afriskaut.api.models.search.Position
import com.naemo.afriskaut.api.models.search.Stat
import com.naemo.afriskaut.db.local.room.ArrayConverter

@Entity(tableName = "player_table")
data class Player(
    @SerializedName("birthcountry")
    val birthcountry: String?,
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("birthplace")
    val birthplace: String?,
    @SerializedName("common_name")
    val commonName: String?,
    @SerializedName("country_id")
    val countryId: Int?,
    @SerializedName("display_name")
    val displayName: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("fullname")
    val fullname: String?,
    @SerializedName("height")
    val height: String?,
    @PrimaryKey
    @SerializedName("_id")
    val id: String,
    @SerializedName("image_path")
    val imagePath: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("player_id")
    val playerId: Int?,
    @SerializedName("position")
    @Embedded
    val position: Position?,
    @SerializedName("position_id")
    val positionId: Int?,
    @SerializedName("stats")
    @TypeConverters(ArrayConverter::class)
    val stats: List<Stat>?,
    @SerializedName("team_id")
    val teamId: Int?,
    @SerializedName("team_name")
    val teamName: String?,
    @SerializedName("weight")
    val weight: String?,
    @SerializedName("following")
    val following: Boolean?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Position::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createTypedArrayList(Stat),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(birthcountry)
        parcel.writeString(birthdate)
        parcel.writeString(birthplace)
        parcel.writeString(commonName)
        parcel.writeValue(countryId)
        parcel.writeString(displayName)
        parcel.writeString(firstname)
        parcel.writeString(fullname)
        parcel.writeString(height)
        parcel.writeString(id)
        parcel.writeString(imagePath)
        parcel.writeString(lastname)
        parcel.writeString(nationality)
        parcel.writeValue(playerId)
        parcel.writeParcelable(position, flags)
        parcel.writeValue(positionId)
        parcel.writeTypedList(stats)
        parcel.writeValue(teamId)
        parcel.writeString(teamName)
        parcel.writeString(weight)
        parcel.writeValue(following)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }
}