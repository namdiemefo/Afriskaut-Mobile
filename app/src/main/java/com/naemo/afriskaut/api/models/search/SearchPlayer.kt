package com.naemo.afriskaut.api.models.search


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("appearences")
    val appearences: Int?,
    @SerializedName("assists")
    val assists: Int?,
    @SerializedName("blocks")
    val blocks: Int?,
    @SerializedName("captain")
    val captain: Int?,
    @SerializedName("crosses")
    val crosses: Crosses?,
    @SerializedName("dispossesed")
    val dispossesed: Int?,
    @SerializedName("dribbles")
    val dribbles: Dribbles?,
    @SerializedName("duels")
    val duels: Duels?,
    @SerializedName("fouls")
    val fouls: Fouls?,
    @SerializedName("goals")
    val goals: Int?,
    @SerializedName("hit_post")
    val hitPost: Int?,
    @SerializedName("inside_box_saves")
    val insideBoxSaves: Int?,
    @SerializedName("interceptions")
    val interceptions: Int?,
    @SerializedName("league_id")
    val leagueId: Int?,
    @SerializedName("league_name")
    val leagueName: String?,
    @SerializedName("lineups")
    val lineups: Int?,
    @SerializedName("minutes")
    val minutes: Int?,
    @SerializedName("owngoals")
    val owngoals: Int?,
    @SerializedName("passes")
    val passes: Passes?,
    @SerializedName("penalties")
    val penalties: Penalties?,
    @SerializedName("player_id")
    val playerId: Int?,
    @SerializedName("redcards")
    val redcards: Int?,
    @SerializedName("saves")
    val saves: Int?,
    @SerializedName("season_id")
    val seasonId: Int?,
    @SerializedName("season_name")
    val seasonName: String?,
    @SerializedName("stat_id")
    val statId: Int?,
    @SerializedName("substitute_in")
    val substituteIn: Int?,
    @SerializedName("substitute_out")
    val substituteOut: Int?,
    @SerializedName("substitutes_on_bench")
    val substitutesOnBench: Int?,
    @SerializedName("tackles")
    val tackles: Int?,
    @SerializedName("team_id")
    val teamId: Int?,
    @SerializedName("team_name")
    val teamName: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("yellowcards")
    val yellowcards: Int?,
    @SerializedName("yellowred")
    val yellowred: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Crosses::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Dribbles::class.java.classLoader),
        parcel.readParcelable(Duels::class.java.classLoader),
        parcel.readParcelable(Fouls::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Passes::class.java.classLoader),
        parcel.readParcelable(Penalties::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(appearences)
        parcel.writeValue(assists)
        parcel.writeValue(blocks)
        parcel.writeValue(captain)
        parcel.writeParcelable(crosses, flags)
        parcel.writeValue(dispossesed)
        parcel.writeParcelable(dribbles, flags)
        parcel.writeParcelable(duels, flags)
        parcel.writeParcelable(fouls, flags)
        parcel.writeValue(goals)
        parcel.writeValue(hitPost)
        parcel.writeValue(insideBoxSaves)
        parcel.writeValue(interceptions)
        parcel.writeValue(leagueId)
        parcel.writeString(leagueName)
        parcel.writeValue(lineups)
        parcel.writeValue(minutes)
        parcel.writeValue(owngoals)
        parcel.writeParcelable(passes, flags)
        parcel.writeParcelable(penalties, flags)
        parcel.writeValue(playerId)
        parcel.writeValue(redcards)
        parcel.writeValue(saves)
        parcel.writeValue(seasonId)
        parcel.writeString(seasonName)
        parcel.writeValue(statId)
        parcel.writeValue(substituteIn)
        parcel.writeValue(substituteOut)
        parcel.writeValue(substitutesOnBench)
        parcel.writeValue(tackles)
        parcel.writeValue(teamId)
        parcel.writeString(teamName)
        parcel.writeString(type)
        parcel.writeValue(yellowcards)
        parcel.writeValue(yellowred)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stat> {
        override fun createFromParcel(parcel: Parcel): Stat {
            return Stat(parcel)
        }

        override fun newArray(size: Int): Array<Stat?> {
            return arrayOfNulls(size)
        }
    }
}

data class Crosses(
    @SerializedName("accurate")
    val accurate: Int?,
    @SerializedName("total")
    val total: Int?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(accurate)
        parcel.writeValue(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Crosses> {
        override fun createFromParcel(parcel: Parcel): Crosses {
            return Crosses(parcel)
        }

        override fun newArray(size: Int): Array<Crosses?> {
            return arrayOfNulls(size)
        }
    }
}

data class Dribbles(
    @SerializedName("attempts")
    val attempts: Int?,
    @SerializedName("dribbled_past")
    val dribbledPast: Int?,
    @SerializedName("success")
    val success: Int?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(attempts)
        parcel.writeValue(dribbledPast)
        parcel.writeValue(success)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dribbles> {
        override fun createFromParcel(parcel: Parcel): Dribbles {
            return Dribbles(parcel)
        }

        override fun newArray(size: Int): Array<Dribbles?> {
            return arrayOfNulls(size)
        }
    }
}

data class Duels(
    @SerializedName("total")
    val total: Int?,
    @SerializedName("won")
    val won: Int?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(total)
        parcel.writeValue(won)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Duels> {
        override fun createFromParcel(parcel: Parcel): Duels {
            return Duels(parcel)
        }

        override fun newArray(size: Int): Array<Duels?> {
            return arrayOfNulls(size)
        }
    }
}

data class Fouls(
    @SerializedName("committed")
    val committed: Int?,
    @SerializedName("drawn")
    val drawn: Int?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(committed)
        parcel.writeValue(drawn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Fouls> {
        override fun createFromParcel(parcel: Parcel): Fouls {
            return Fouls(parcel)
        }

        override fun newArray(size: Int): Array<Fouls?> {
            return arrayOfNulls(size)
        }
    }
}

data class Passes(
    @SerializedName("accuracy")
    val accuracy: Int?,
    @SerializedName("key_passes")
    val keyPasses: Int?,
    @SerializedName("total")
    val total: Int?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(accuracy)
        parcel.writeValue(keyPasses)
        parcel.writeValue(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Passes> {
        override fun createFromParcel(parcel: Parcel): Passes {
            return Passes(parcel)
        }

        override fun newArray(size: Int): Array<Passes?> {
            return arrayOfNulls(size)
        }
    }
}

data class Penalties(
    @SerializedName("committed")
    val committed: Int?,
    @SerializedName("missed")
    val missed: Int?,
    @SerializedName("saves")
    val saves: Int?,
    @SerializedName("scores")
    val scores: Int?,
    @SerializedName("won")
    val won: Int?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(committed)
        parcel.writeValue(missed)
        parcel.writeValue(saves)
        parcel.writeValue(scores)
        parcel.writeValue(won)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Penalties> {
        override fun createFromParcel(parcel: Parcel): Penalties {
            return Penalties(parcel)
        }

        override fun newArray(size: Int): Array<Penalties?> {
            return arrayOfNulls(size)
        }
    }
}

data class Position(
    @SerializedName("id")
    val pid: Int?,
    @SerializedName("name")
    val name: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(pid)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Position> {
        override fun createFromParcel(parcel: Parcel): Position {
            return Position(parcel)
        }

        override fun newArray(size: Int): Array<Position?> {
            return arrayOfNulls(size)
        }
    }
}