package com.naemo.afriskaut.db.local.room.following

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.naemo.afriskaut.db.local.room.stats.ArrayConverter

@Entity(tableName = "following_player")
data class FollowingData(
    @SerializedName("dob")
    val dob: String?,
    @SerializedName("following")
    val following: Boolean?,
    @SerializedName("fullname")
    val fullname: String?,
    @SerializedName("height")
    val height: String?,
    @SerializedName("_id")
    val _id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("player_id")
    val playerId: String?,
    @SerializedName("position")
    val position: String?,
    @SerializedName("team")
    val team: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Dao
interface FollowingPlayerDao {

    @Query("SELECT * FROM following_player")
    fun loadFollowing(): LiveData<List<FollowingData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFollow(data: FollowingData)

    @Query("SELECT * FROM following_player WHERE fullname = :name")
    fun searchFor(name: String): LiveData<List<FollowingData>>


    @Query("DELETE FROM following_player")
    fun deleteRadar()
}

@Database(entities = [FollowingData::class], version = 3, exportSchema = false)
@TypeConverters(ArrayConverter::class)
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
            Room.databaseBuilder(context, FollowingPlayerDataBase::class.java, "Following Player")
                .fallbackToDestructiveMigration()
                .build()
    }
}