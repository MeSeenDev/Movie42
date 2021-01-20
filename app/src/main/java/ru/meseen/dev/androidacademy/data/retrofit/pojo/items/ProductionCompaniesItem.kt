package ru.meseen.dev.androidacademy.data.retrofit.pojo.items

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param id: id компании
 * @param logoPath: Адресс изображения компании
 * @param name: Имя компании
 * @param originCountry: Расположение компании стандарт iso_3166_1
 */
@Serializable
data class ProductionCompaniesItem(

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("logo_path")
    val logoPath: String? = null,

    @SerialName("origin_country")
    val originCountry: String
)