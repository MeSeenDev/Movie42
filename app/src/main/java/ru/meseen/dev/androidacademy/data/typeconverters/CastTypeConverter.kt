package ru.meseen.dev.androidacademy.data.typeconverters

import androidx.room.TypeConverter
import ru.meseen.dev.androidacademy.data.CastData
import ru.meseen.dev.androidacademy.data.GenreData

class CastTypeConverter {

    @TypeConverter
    fun listCastDataToString(cast: List<CastData>): String =
        cast.asSequence().map { "${it.id}↓${it.name}↓${it.drawableUrl}" }
            .joinToString(separator = "■")


    @TypeConverter
    fun stringCastDataToList(value: String): List<CastData> =
        if (value.isNotEmpty()) value.split("■").asSequence().map {
            val temp = it.split("↓")
            CastData(
                temp[0].toInt(),
                temp[1],
                temp[2]
            )
        }.toList() else listOf(
            CastData(
                0,
                "Empty list",
                "https://www.santek2.ru/local/templates/santek/img/no-photo.jpg"
            )
        )

    @TypeConverter
    fun listGenreToString(cast: List<GenreData>): String =
        cast.asSequence().map { "${it.id}↓${it.name}" }.joinToString(separator = "■")

    @TypeConverter
    fun stringGenreToList(value: String): List<GenreData> =
        if (value.isNotEmpty()) value.split("■").asSequence().map {
            val temp = it.split("↓")
            GenreData(
                id = temp[0].toInt(),
                temp[1]
            )
        }.toList() else listOf(GenreData(name = "..."))
}