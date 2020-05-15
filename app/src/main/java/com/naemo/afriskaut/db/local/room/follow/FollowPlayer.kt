package com.naemo.afriskaut.db.local.room.follow

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "follow_player")
data class FolloData(
    @SerializedName("id")
    val playerId: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("position")
    val position: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}

@Dao
interface FollowPlayerDao {

    @Query("SELECT * FROM follow_player")
    fun loadFollow(): LiveData<FolloData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFollow(data: FolloData)

    @Query("SELECT * FROM follow_player WHERE playerId = :playerId")
    fun searchFollow(playerId: String): Boolean

    @Query("DELETE FROM follow_player")
    fun deleteFollow()
}


@Database(entities = [FolloData::class], version = 1, exportSchema = false)
abstract class FollowPlayerDataBase: RoomDatabase() {

    abstract fun followplayerdao(): FollowPlayerDao

    companion object {
        @Volatile
        private var instance: FollowPlayerDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, FollowPlayerDataBase::class.java, "Follow Player").build()
    }
}