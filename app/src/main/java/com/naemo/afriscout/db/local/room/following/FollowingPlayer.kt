package com.naemo.afriscout.db.local.room.following

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "following_player")
data class FollowingData(
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("_id")
    val playerId: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("team")
    val team: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}

@Dao
interface FollowingPlayerDao {

    @Query("SELECT * FROM following_player")
    fun loadFollowing(): LiveData<FollowingData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFollow(data: FollowingData)

    @Query("SELECT * FROM following_player WHERE playerId = :playerId")
    fun searchFollowing(playerId: String): Boolean
}

@Database(entities = [FollowingData::class], version = 1, exportSchema = false)
abstract class FollowingPlayerDataBase: RoomDatabase() {

    abstract fun followingplayerdao(): FollowingPlayerDao

    companion object {
        @Volatile
        private var instance: FollowingPlayerDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, FollowingPlayerDataBase::class.java, "Following Player").build()
    }
}