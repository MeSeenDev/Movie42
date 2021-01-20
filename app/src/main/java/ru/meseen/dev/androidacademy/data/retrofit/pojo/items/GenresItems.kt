package ru.meseen.dev.androidacademy.data.retrofit.pojo.items

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresItems(
    @SerialName("genres")
    val genres: List<GenresItem>
)

/**
 * @param genresId: id жанра таблицы
 * @param genresName: Наименование жанра
 */
@Serializable
data class GenresItem(

    @SerialName("id")
    val genresId: Int,

    @SerialName("name")
    val genresName: String

)