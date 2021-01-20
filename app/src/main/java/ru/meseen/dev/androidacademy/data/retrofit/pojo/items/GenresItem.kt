package ru.meseen.dev.androidacademy.data.retrofit.pojo.items

import com.google.gson.annotations.SerializedName

/**
 * @param id: id жанра таблицы
 * @param name: Наименование жанра
 */
data class GenresItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)