package com.naemo.afriscout.db.local.room.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_table")
data class Data(
    @SerializedName("country_id")
    val countryId: Int,
    @SerializedName("_id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("player_id")
    val playerId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var vId = 0
}

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_table")
    fun loadSearch(): LiveData<Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSearch(search: Data)

    @Query("DELETE FROM search_table")
    fun deleteSearch()
}

@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun searchDao(): SearchDao

    companion object {
        @Volatile
        private var instance: SearchDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, SearchDatabase::class.java, "Search").build()

    }

}