package com.naemo.afriskaut.db.local.room.scout

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.naemo.afriskaut.api.models.search.Stats
import com.naemo.afriskaut.db.local.room.ArrayConverter

@Entity(tableName = "scout_table")
data class Player(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("birthcountry")
    val birthcountry: String?,
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("birthplace")
    val birthplace: String?,
    @SerializedName("country_id")
    val countryId: Int?,
    @SerializedName("display_name")
    val displayName: String?,
    @SerializedName("dob")
    val dob: String?,
    @SerializedName("following")
    val following: Boolean?,
    @SerializedName("fullname")
    val fullname: String?,
    @SerializedName("height")
    val height: String?,
    @SerializedName("_id")
    @PrimaryKey
    val id: String,
    @SerializedName("image_path")
    val imagePath: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("position")
    @Embedded
    val position: Stats.Position?,
    @SerializedName("score")
    val score: Double?,
    @SerializedName("stats")
    @TypeConverters(ArrayConverter::class)
    val stats: List<Stats>?,
    @SerializedName("weight")
    val weight: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Stats.Position::class.java.classLoader),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.createTypedArrayList(Stats),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(age)
        parcel.writeString(birthcountry)
        parcel.writeString(birthdate)
        parcel.writeString(birthplace)
        parcel.writeValue(countryId)
        parcel.writeString(displayName)
        parcel.writeString(dob)
        parcel.writeValue(following)
        parcel.writeString(fullname)
        parcel.writeString(height)
        parcel.writeString(id)
        parcel.writeString(imagePath)
        parcel.writeString(nationality)
        parcel.writeParcelable(position, flags)
        parcel.writeValue(score)
        parcel.writeTypedList(stats)
        parcel.writeString(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }
}

@Dao
interface ScoutDao {

    @Query("SELECT * FROM scout_table")
    fun loadSearch(): LiveData<List<Player>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSearch(search: Player)

    @Query("DELETE FROM scout_table")
    fun deleteSearch()
}

@Database(entities = [Player::class], version = 4, exportSchema = false)
@TypeConverters(ArrayConverter::class)
abstract class ScoutDatabase : RoomDatabase() {

    abstract fun scoutDao(): ScoutDao

    companion object {
        @Volatile
        private var instance: ScoutDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(
                    context
                ).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, ScoutDatabase::class.java, "Scout_Player")
                .fallbackToDestructiveMigration()
                .build()

//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//
//                database.execSQL(
//                    "ALTER TABLE search_table " + " ADD COLUMN height TEXT" + " ADD COLUMN dob TEXT" +
//                            " ADD COLUMN team TEXT" + " ADD COLUMN image TEXT" + " ADD COLUMN nationality TEXT" +
//                            " ADD COLUMN position TEXT" + " ADD COLUMN following REAL"
//                )
//            }
//        }


    }
}