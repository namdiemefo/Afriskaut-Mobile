package com.naemo.afriskaut.db.local.room.stats

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface PlayerDao {
    @Transaction
    @Query("SELECT * FROM stats")
    fun loadPlayerStats(): Player
}
@Dao
interface StatsDao {
    @Query("SELECT * FROM stats")
    fun loadStats(): LiveData<Stats>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveStats(stats: Stats)

    @Query("SELECT * FROM stats WHERE playerId =:playerId ")
    fun loadOneStat(playerId: Int): LiveData<Stats>

    @Query("DELETE FROM stats")
    fun deleteStats()
}

@Dao
interface PlayerStatsDao {
    @Query("SELECT * FROM player_stats")
    fun loadPlayerStats(): LiveData<PlayerStats>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlayerStats(stat: List<PlayerStats>)

    @Query("SELECT * FROM player_stats WHERE teamId = :id ")
    fun loadPlayerTeamStats(id: Int): LiveData<PlayerStats>

    @Query("DELETE FROM player_stats")
    fun deletePlayerStats()
}

@Dao
interface CrossesDao {
    @Query("SELECT * FROM crosses")
    fun loadCrosses(): LiveData<Crosses>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCrosses(crosses: Crosses)

    @Query("DELETE FROM crosses")
    fun deleteCrosses()
}

@Dao
interface DribblesDao {
    @Query("SELECT * FROM dribbles")
    fun loadDribbles(): LiveData<Dribbles>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDribbles(dribbles: Dribbles)

    @Query("DELETE FROM dribbles")
    fun deleteDribbles()
}

@Dao
interface DuelsDao {
    @Query("SELECT * FROM duels")
    fun loadDuels(): LiveData<Duels>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDuels(duels: Duels)

    @Query("DELETE FROM duels")
    fun deleteDuels()
}

@Dao
interface FoulsDao {
    @Query("SELECT * FROM fouls")
    fun loadFouls(): LiveData<Fouls>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFouls(fouls: Fouls)

    @Query("DELETE FROM fouls")
    fun deleteFouls()
}

@Dao
interface PassesDao {
    @Query("SELECT * FROM passes")
    fun loadPasses(): LiveData<Passes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePasses(passes: Passes)

    @Query("DELETE FROM passes")
    fun deletePasses()
}

@Dao
interface PenaltiesDao {
    @Query("SELECT * FROM penalties")
    fun loadPenalties(): LiveData<Penalties>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePenalties(penalties: Penalties)

    @Query("DELETE FROM penalties")
    fun deletePenalties()
}

