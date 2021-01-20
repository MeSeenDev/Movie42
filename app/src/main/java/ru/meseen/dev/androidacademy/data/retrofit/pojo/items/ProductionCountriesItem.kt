package ru.meseen.dev.androidacademy.data.retrofit.pojo.items

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * @param countryCode по стандадарту iso_3166_1
 * @param name Название Страны
 */
@Serializable
data class ProductionCountriesItem(
    @SerialName("iso_3166_1")
    val countryCode: String,

    @SerialName("name")
    val name: String,
)
