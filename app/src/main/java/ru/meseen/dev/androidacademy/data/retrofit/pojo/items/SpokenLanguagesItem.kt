package ru.meseen.dev.androidacademy.data.retrofit.pojo.items

import com.google.gson.annotations.SerializedName

/**
 * @param name: Название языка на английском "Deutsch"
 * @param iso6391: изык по стандарту  "de"
 * @param englishName: страна на английском языке "German"
 */
data class SpokenLanguagesItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("iso_639_1")
    val iso6391: String,

    @field:SerializedName("english_name")
    val englishName: String
)