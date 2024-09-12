package com.example.storage.data.db

import androidx.room.TypeConverter

object RoomConverters {

    @TypeConverter
    @JvmStatic
    fun fromListToString(list: List<String>): String = list.joinToString()

    @TypeConverter
    @JvmStatic
    fun fromStringToList(str: String): List<String> {
        return if (str.isEmpty()) {
            emptyList()
        } else {
            str.split(", ")
        }
    }
}
