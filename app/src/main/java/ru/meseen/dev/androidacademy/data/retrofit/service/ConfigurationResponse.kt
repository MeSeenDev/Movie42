package ru.meseen.dev.androidacademy.data.retrofit.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * @param imagesConfigKeys конфигурация изображений
 * @param changeKeys ключевые слова
 * @see <a href="https://developers.themoviedb.org/3/configuration/get-api-configuration">API Get the system wide configuration information</a>
 */
@Serializable
data class ConfigurationResponse(

	@SerialName("images")
	val imagesConfigKeys: ImagesConfig,

	@SerialName("change_keys")
	val changeKeys: List<String>
)

/**
 * @param posterSizes ключи размеров постеров
 * @param secureBaseUrl URL для загрузки изображения по https
 * @param backdropSizes размеры задника изодражений
 * @param baseUrl URL для загрузки изображения http
 * @param logoSizes размеры логотипов компаний
 * @param stillSizes
 * @param profileSizes
 */
@Serializable
data class ImagesConfig(

	@SerialName("poster_sizes")
	val posterSizes: List<String>,

	@SerialName("secure_base_url")
	val secureBaseUrl: String,

	@SerialName("backdrop_sizes")
	val backdropSizes: List<String>,

	@SerialName("base_url")
	val baseUrl: String,

	@SerialName("logo_sizes")
	val logoSizes: List<String>,

	@SerialName("still_sizes")
	val stillSizes: List<String>,

	@SerialName("profile_sizes")
	val profileSizes: List<String>
)
