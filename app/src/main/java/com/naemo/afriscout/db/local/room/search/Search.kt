package com.naemo.afriscout.db.local.room.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_table")
data class Data(
    @SerializedName("country_id")
    @ColumnInfo(name = "countryId")
    val countryId: Int,
    @SerializedName("height")
    val height: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("team")
    val team: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("player_id")
    val playerId: Int,
    @SerializedName("following")
    val following: Boolean
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

@Database(entities = [Data::class], version = 2, exportSchema = false)
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
            Room.databaseBuilder(context, SearchDatabase::class.java, "Search")
                .fallbackToDestructiveMigration()
                .build()

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("ALTER TABLE search_table " + " ADD COLUMN height TEXT" + " ADD COLUMN dob TEXT" +
                 " ADD COLUMN team TEXT" + " ADD COLUMN image TEXT" + " ADD COLUMN nationality TEXT" +
                 " ADD COLUMN position TEXT" + " ADD COLUMN following REAL")
            }
        }


    }

}