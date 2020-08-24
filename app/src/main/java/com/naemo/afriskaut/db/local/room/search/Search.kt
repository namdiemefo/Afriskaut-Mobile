package com.naemo.afriskaut.db.local.room.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.naemo.afriskaut.db.local.room.ArrayConverter

@Dao
interface SearchDao {

    @Query("SELECT * FROM player_table")
    fun loadSearch(): List<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSearch(search: Player)

    @Query("UPDATE player_table SET following=:following WHERE id=:id")
    fun update(following: Boolean, id: String)



    @Query("DELETE FROM player_table")
    fun deleteSearch()
}

@Database(entities = [Player::class], version = 5, exportSchema = false)
@TypeConverters(ArrayConverter::class)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun searchDao(): SearchDao

    companion object {
        @Volatile
        private var instance: SearchDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(
                        context
                    ).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, SearchDatabase::class.java, "Search_Player")
                .fallbackToDestructiveMigration()
                .build()

//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//
//                database.execSQL("ALTER TABLE search_table " + " ADD COLUMN height TEXT" + " ADD COLUMN dob TEXT" +
//                 " ADD COLUMN team TEXT" + " ADD COLUMN image TEXT" + " ADD COLUMN nationality TEXT" +
//                 " ADD COLUMN position TEXT" + " ADD COLUMN following REAL")
//            }
//        }


    }

}