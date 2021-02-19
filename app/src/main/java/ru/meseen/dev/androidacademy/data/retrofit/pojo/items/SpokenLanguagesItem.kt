package ru.meseen.dev.androidacademy.data.retrofit.pojo.items

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param name: Название языка на английском "Deutsch"
 * @param iso6391: изык по стандарту iso_639_1 "de"
 * @param englishName: страна на английском языке "German"
 */
@Serializable
data class SpokenLanguagesItem(

    @SerialName("name")
    val name: String = "none",

    @SerialName("iso_639_1")
    val iso6391: String = "none",

    @SerialName("english_name")
    val englishName: String = "none"
)