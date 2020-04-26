package com.naemo.afriskaut.db.local.room.stats

import androidx.room.*
import com.google.gson.annotations.SerializedName


data class Player(
    @Embedded
    val stats: Stats,
    @Relation(
        parentColumn = "playerId",
        entityColumn = "player_id"
    )
    val playerStats: List<PlayerStats>? = ArrayList()
)

@Entity(tableName = "stats")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class Stats(
    @SerializedName("_id")
    @ColumnInfo(name = "playerstatsid")
    var playerStatsId: String?,
    @SerializedName("player_id")
    @ColumnInfo(name = "playerId")
    var playerId: Int?,
    @SerializedName("stats")
    var playerstats: List<PlayerStats>? = ArrayList()
) {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id = 0
}

@Entity(tableName = "player_stats")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class PlayerStats(
    @SerializedName("appearences")
    val appearences: Int?,
    @SerializedName("assists")
    val assists: Int?,
    @SerializedName("blocks")
    val blocks: Any?,
    @SerializedName("captain")
    val captain: Int?,
    @SerializedName("crosses")
    @Embedded
    val crosses: Crosses?,
    @SerializedName("dispossesed")
    val dispossesed: Int?,
    @SerializedName("dribbles")
    @Embedded
    val dribbles: Dribbles?,
    @SerializedName("duels")
    @Embedded
    val duels: Duels?,
    @SerializedName("fouls")
    @Embedded
    val fouls: Fouls?,
    @SerializedName("goals")
    val goals: Int?,
    @SerializedName("hit_post")
    val hitPost: Any?,
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
    @SerializedName("passes")
    @Embedded
    val passes: Passes?,
    @SerializedName("penalties")
    @Embedded
    val penalties: Penalties?,
    @SerializedName("player_id")
    @ColumnInfo(name = "player_id")
    val playerId: Int?,
    @SerializedName("player_name")
    val playerName: String?,
    @SerializedName("redcards")
    val redcards: Int?,
    @SerializedName("saves")
    val saves: Int?,
    @SerializedName("season_id")
    val seasonId: Int?,
    @SerializedName("season_name")
    val seasonName: String?,
    @SerializedName("substitute_in")
    val substituteIn: Int?,
    @SerializedName("substitute_out")
    val substituteOut: Int?,
    @SerializedName("substitutes_on_bench")
    val substitutesOnBench: Int?,
    @SerializedName("tackles")
    val tackles: Any?,
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
) {
    @PrimaryKey(autoGenerate = true)
    var sId = 0
}

@Entity(tableName = "crosses")
data class Crosses(
    @SerializedName("accurate")
    val accurate: Any?,
    @SerializedName("total")
    val crossesTotal: Any?
){
    @PrimaryKey(autoGenerate = true)
    var cId = 0
}

@Entity(tableName = "dribbles")
data class Dribbles(
    @SerializedName("attempts")
    val attempts: Any?,
    @SerializedName("dribbled_past")
    val dribbledPast: Any?,
    @SerializedName("success")
    val success: Any?
) {
    @PrimaryKey(autoGenerate = true)
    var drId = 0
}

@Entity(tableName = "duels")
data class Duels(
    @SerializedName("total")
    val duelsTotal: Any?,
    @SerializedName("won")
    val duelsWon: Any?
) {
    @PrimaryKey(autoGenerate = true)
    var duId = 0
}

@Entity(tableName = "fouls")
data class Fouls(
    @SerializedName("committed")
    val foulsCommitted: Any?,
    @SerializedName("drawn")
    val drawn: Any?
) {
    @PrimaryKey(autoGenerate = true)
    var fId = 0
}

@Entity(tableName = "passes")
data class Passes(
    @SerializedName("accuracy")
    val accuracy: Any?,
    @SerializedName("key_passes")
    val keyPasses: Any?,
    @SerializedName("total")
    val passTotal: Any?
) {
    @PrimaryKey(autoGenerate = true)
    var paId = 0
}

@Entity(tableName = "penalties")
data class Penalties(
    @SerializedName("committed")
    val committed: Any?,
    @SerializedName("missed")
    val missed: Any?,
    @SerializedName("saves")
    val pkSaves: Any?,
    @SerializedName("scores")
    val scores: Any?,
    @SerializedName("won")
    val won: Any?
) {
    @PrimaryKey(autoGenerate = true)
    var peId = 0
}