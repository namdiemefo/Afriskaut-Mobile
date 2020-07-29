package com.naemo.afriskaut.db.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.naemo.afriskaut.api.models.search.Stat
import com.naemo.afriskaut.db.local.room.search.Player


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
    fun fromPlayerStatsList(stat: List<Stat?>?): String? {
        if (stat == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Stat?>?>() {}.type
        return gson.toJson(stat, type)
    }

    @TypeConverter
    fun toPlayerStatsList(stat: String?) : MutableList<Stat>? {
        if (stat ==  null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<List<Stat?>?>() {}.type
        return gson.fromJson(stat, type)

    }
}