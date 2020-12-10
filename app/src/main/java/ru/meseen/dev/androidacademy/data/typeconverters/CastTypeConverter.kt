package ru.meseen.dev.androidacademy.data.typeconverters

import androidx.room.TypeConverter
import ru.meseen.dev.androidacademy.data.CastData

class CastTypeConverter {

    @TypeConverter
    fun listToString(cast: List<CastData>): String =
        cast.asSequence().map { "${it.name}↓${it.drawable}" }.joinToString(separator = "■")

    @TypeConverter
    fun stringToList(value: String): List<CastData> = value.split("■").asSequence().map {
        val temp = it.split("↓")
        CastData(temp[0], temp[1].toInt())
    }.toList()
}