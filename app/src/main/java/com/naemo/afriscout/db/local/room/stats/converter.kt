package com.naemo.afriscout.db.local.room.stats

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AnyConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun toString(data: Any?): Int? {
            return data.toString().toIntOrNull()
        }

        @TypeConverter
        @JvmStatic
        fun toData(data: Int?): Any? = data
    }


}

class ArrayConverter {
    @TypeConverter
    fun fromPlayerStatsList(playerStats: List<PlayerStats?>?): String? {
        if (playerStats == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<PlayerStats?>?>() {}.type
        return gson.toJson(playerStats, type)
    }

    @TypeConverter
    fun toPlayerStatsList(playerStats: String?) : MutableList<PlayerStats>? {
        if (playerStats ==  null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<List<PlayerStats?>?>() {}.type
        return gson.fromJson(playerStats, type)

    }
}