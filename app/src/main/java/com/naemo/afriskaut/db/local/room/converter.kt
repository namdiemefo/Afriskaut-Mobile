package com.naemo.afriskaut.db.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.naemo.afriskaut.api.models.search.Stats


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
    fun fromPlayerStatsList(stat: List<Stats?>?): String? {
        if (stat == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Stats?>?>() {}.type
        return gson.toJson(stat, type)
    }

    @TypeConverter
    fun toPlayerStatsList(stat: String?) : MutableList<Stats>? {
        if (stat ==  null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<List<Stats?>?>() {}.type
        return gson.fromJson(stat, type)

    }
}