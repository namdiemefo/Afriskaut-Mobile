package com.naemo.afriskaut.db.local.room.suggest

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.naemo.afriskaut.api.models.search.Stats
import com.naemo.afriskaut.db.local.room.ArrayConverter

@Entity(tableName = "suggest_table")
data class SuggestData(
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

    companion object CREATOR : Parcelable.Creator<SuggestData> {
        override fun createFromParcel(parcel: Parcel): SuggestData {
            return SuggestData(parcel)
        }

        override fun newArray(size: Int): Array<SuggestData?> {
            return arrayOfNulls(size)
        }
    }
}

@Dao
interface SuggestionDao {

    @Query("SELECT * FROM suggest_table")
    fun loadSuggestion(): LiveData<List<SuggestData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSuggestion(search: SuggestData)

    @Query("DELETE FROM suggest_table")
    fun deleteSuggestion()
}

@Database(entities = [SuggestData::class], version = 2, exportSchema = false)
@TypeConverters(ArrayConverter::class)
abstract class SuggestionDatabase : RoomDatabase() {

    abstract fun suggestionDao(): SuggestionDao

    companion object {
        @Volatile
        private var instance: SuggestionDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(
                    context
                ).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, SuggestionDatabase::class.java, "Suggest_Player")
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

