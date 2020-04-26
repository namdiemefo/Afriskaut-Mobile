package com.naemo.afriskaut.db.local.room.stats

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class StatsRepository(application: Application): CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val TAG = "statsRepository"
    var playerDao: PlayerDao? = null
    var statsDao: StatsDao? = null
    var playerStatsDao: PlayerStatsDao? = null
    var crossesDao: CrossesDao? = null
    var dribblesDao: DribblesDao? = null
    var duelsDao: DuelsDao? = null
    var foulsDao: FoulsDao? = null
    var passesDao: PassesDao? = null
    var penaltiesDao: PenaltiesDao? = null

    init {
        val database = StatsDatabase.invoke(application)
        playerDao = database.playerDao()
        statsDao = database.statsDao()
        playerStatsDao = database.playerstatsDao()
        crossesDao = database.crossesDao()
        dribblesDao = database.dribblesDao()
        duelsDao = database.duelsDao()
        foulsDao = database.foulsDao()
        passesDao = database.passesDao()
        penaltiesDao = database.penaltiesDao()
    }

    fun getPlayer(): Player? {
        return playerDao?.loadPlayerStats()
    }

    fun saveStats(stats: Stats){
        launch {
            saveIt(stats)
        }
    }

    private suspend fun saveIt(stats: Stats) {
        withContext(IO) {
            statsDao?.deleteStats()
            statsDao?.saveStats(stats)
        }
    }

    fun getStats(): LiveData<Stats>? {
        return statsDao?.loadStats()
    }

    fun loadOne(playerId: Int): LiveData<Stats>? {
        return statsDao?.loadOneStat(playerId)
    }

    fun savePlayerStats(playerStats: List<PlayerStats>) {
        launch {
            setStats(playerStats)
        }
    }

    private suspend fun setStats(playerStats: List<PlayerStats>) {
        withContext(IO) {
            playerStatsDao?.deletePlayerStats()
            playerStatsDao?.savePlayerStats(playerStats)
        }
    }

    fun getPlayerStats(): LiveData<PlayerStats>? {
        return playerStatsDao?.loadPlayerStats()
    }

    fun getPlayerTeamStats(id: Int): LiveData<PlayerStats>? {
        return playerStatsDao?.loadPlayerTeamStats(id)
    }

    fun saveCrosses(crosses: Crosses) {
        launch {
            setCrosses(crosses)
        }
    }

    private suspend fun setCrosses(crosses: Crosses) {
        withContext(IO) {
            crossesDao?.deleteCrosses()
            crossesDao?.saveCrosses(crosses)
        }
    }

    fun getCrosses(): LiveData<Crosses>? {
        return crossesDao?.loadCrosses()
    }

    fun saveDribbles(dribbles: Dribbles) {
        launch {
            setDribbles(dribbles)
        }
    }

    private suspend fun setDribbles(dribbles: Dribbles) {
        withContext(IO) {
            dribblesDao?.deleteDribbles()
            dribblesDao?.saveDribbles(dribbles)
        }
    }

    fun getDribbles(): LiveData<Dribbles>? {
        return dribblesDao?.loadDribbles()
    }

    fun saveDuels(duels: Duels) {
        launch {
            setDuels(duels)
        }
    }

    private suspend fun setDuels(duels: Duels) {
        withContext(IO) {
            duelsDao?.deleteDuels()
            duelsDao?.saveDuels(duels)
        }
    }

    fun getDuels(): LiveData<Duels>? {
        return duelsDao?.loadDuels()
    }

    fun saveFouls(fouls: Fouls) {
        launch {
            setFouls(fouls)
        }
    }

    private suspend fun setFouls(fouls: Fouls) {
        withContext(IO) {
            foulsDao?.deleteFouls()
            foulsDao?.saveFouls(fouls)
        }
    }

    fun getFouls(): LiveData<Fouls>? {
        return foulsDao?.loadFouls()
    }

    fun savePasses(passes: Passes) {
        launch {
            setPasses(passes)
        }
    }

    private suspend fun setPasses(passes: Passes) {
        withContext(IO) {
            passesDao?.deletePasses()
            passesDao?.savePasses(passes)
        }
    }

    fun getPasses(): LiveData<Passes>? {
        return passesDao?.loadPasses()
    }

    fun savePenalties(penalties: Penalties) {
        launch {
            setPenalties(penalties)
        }
    }

    private suspend fun setPenalties(penalties: Penalties) {
        withContext(IO){
            penaltiesDao?.deletePenalties()
            penaltiesDao?.savePenalties(penalties)
        }
    }

    fun getPenalties(): LiveData<Penalties>? {
        return penaltiesDao?.loadPenalties()
    }


}