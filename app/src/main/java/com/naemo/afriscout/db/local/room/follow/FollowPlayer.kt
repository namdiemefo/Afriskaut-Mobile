package com.naemo.afriscout.db.local.room.follow

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.naemo.afriscout.api.models.player.follow.FollowData

@Entity(tableName = "follow_player")
data class FolloData(
    @SerializedName("id")
   // @ColumnInfo(name = "playerId")
    val playerId: String,
    @SerializedName("dob")
   // @ColumnInfo(name = "dob")
    val dob: String,
    @SerializedName("fullname")
  //  @ColumnInfo(name = "fullName")
    val fullname: String,
    @SerializedName("height")
   // @ColumnInfo(name = "height")
    val height: String,
    @SerializedName("image")
   // @ColumnInfo(name = "image")
    val image: String,
    @SerializedName("nationality")
  //  @ColumnInfo(name = "nationality")
    val nationality: String,
    @SerializedName("position")
  //  @ColumnInfo(name = "position")
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