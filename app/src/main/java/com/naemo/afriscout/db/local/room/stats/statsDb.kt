package com.naemo.afriscout.db.local.room.stats

import android.content.Context
import androidx.room.*

@Database(entities = [Stats::class, PlayerStats::class, Crosses::class, Dribbles::class, Duels::class, Fouls::class, Passes::class, Penalties::class], version = 2, exportSchema = false)
@TypeConverters(AnyConverter::class, ArrayConverter::class)
abstract class StatsDatabase: RoomDatabase() {

    abstract fun playerDao(): PlayerDao
    abstract fun statsDao(): StatsDao
    abstract fun playerstatsDao(): PlayerStatsDao
    abstract fun crossesDao(): CrossesDao
    abstract fun dribblesDao(): DribblesDao
    abstract fun duelsDao(): DuelsDao
    abstract fun foulsDao(): FoulsDao
    abstract fun passesDao(): PassesDao
    abstract fun penaltiesDao(): PenaltiesDao

    companion object {
        @Volatile
        private var instance: StatsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, StatsDatabase::class.java, "Player Stats")
                .fallbackToDestructiveMigration()
                .build()
    }

}