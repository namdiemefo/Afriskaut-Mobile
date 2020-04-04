package com.naemo.afriscout.api.models.player.stats


import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("appearences")
    val appearences: Int,
    @SerializedName("assists")
    val assists: Int,
    @SerializedName("blocks")
    val blocks: Any,
    @SerializedName("captain")
    val captain: Int,
    @SerializedName("crosses")
    val crosses: Crosses,
    @SerializedName("dispossesed")
    val dispossesed: Int,
    @SerializedName("dribbles")
    val dribbles: Dribbles,
    @SerializedName("duels")
    val duels: Duels,
    @SerializedName("fouls")
    val fouls: Fouls,
    @SerializedName("goals")
    val goals: Int,
    @SerializedName("hit_post")
    val hitPost: Any,
    @SerializedName("inside_box_saves")
    val insideBoxSaves: Int,
    @SerializedName("interceptions")
    val interceptions: Int,
    @SerializedName("league_id")
    val leagueId: Int,
    @SerializedName("league_name")
    val leagueName: String,
    @SerializedName("lineups")
    val lineups: Int,
    @SerializedName("minutes")
    val minutes: Int,
    @SerializedName("passes")
    val passes: Passes,
    @SerializedName("penalties")
    val penalties: Penalties,
    @SerializedName("player_id")
    val playerId: Int,
    @SerializedName("player_name")
    val playerName: String,
    @SerializedName("redcards")
    val redcards: Int,
    @SerializedName("saves")
    val saves: Int,
    @SerializedName("season_id")
    val seasonId: Int,
    @SerializedName("season_name")
    val seasonName: String,
    @SerializedName("substitute_in")
    val substituteIn: Int,
    @SerializedName("substitute_out")
    val substituteOut: Int,
    @SerializedName("substitutes_on_bench")
    val substitutesOnBench: Int,
    @SerializedName("tackles")
    val tackles: Any,
    @SerializedName("team_id")
    val teamId: Int,
    @SerializedName("team_name")
    val teamName: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("yellowcards")
    val yellowcards: Int,
    @SerializedName("yellowred")
    val yellowred: Int
)