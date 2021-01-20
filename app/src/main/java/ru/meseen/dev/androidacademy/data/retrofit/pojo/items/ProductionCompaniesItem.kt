package ru.meseen.dev.androidacademy.data.retrofit.pojo.items

import com.google.gson.annotations.SerializedName

/**
 * @param id: id компании
 * @param logoPath: Адресс изображения компании
 * @param name: Имя компании
 * @param originCountry: Расположение компании
 */
data class ProductionCompaniesItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

	@field:SerializedName("logo_path")
    val logoPath: Any? = null,

	@field:SerializedName("origin_country")
    val originCountry: String
)