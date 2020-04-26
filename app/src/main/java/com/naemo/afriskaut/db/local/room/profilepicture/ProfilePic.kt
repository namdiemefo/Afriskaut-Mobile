package com.naemo.afriskaut.db.local.room.profilepicture

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "profile_image")
data class ProfilePic(
    @SerializedName("data")
    val data: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuscode")
    val statuscode: Int
) {
    @PrimaryKey(autoGenerate = false)
    var id = 0
}

@Dao
interface ProfilePicDao {

    @Query("SELECT * FROM profile_image")
    fun loadImage(): LiveData<ProfilePic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveImage(image: ProfilePic)

    @Query("DELETE FROM profile_image")
    fun deleteImage()

}

@Database(entities = [ProfilePic::class], version = 1, exportSchema = false)
abstract class ProfilePicDataBase: RoomDatabase() {

    abstract fun profilepicdao(): ProfilePicDao

    companion object {
        @Volatile
        private var instance: ProfilePicDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                ).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, ProfilePicDataBase::class.java, "Profile Image").build()
    }
}