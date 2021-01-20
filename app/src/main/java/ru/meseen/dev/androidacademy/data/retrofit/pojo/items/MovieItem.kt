package ru.meseen.dev.androidacademy.data.retrofit.pojo.items

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param movieId: movie ID
 * @param posterPath: Адрес изображеиня постера
 * @param voteCount: overview количество проголосовавших "votesCount"(vote_count)
 * @param title: Имя фильма Title
 * @param voteAverage: Рейтинг фильма от 0..10 "vote_average"
 * @param genreIds: Жанры фильма
 * runtime: Длительность фильма "runtime" --!!
 * @param originalLanguage: язык оригинала фильма
 * @param originalTitle: Название на оригинальном (Родном) языке
 * @param backdropPath: Адрес изображения на фоне дестрипшн фрагмента
 * @param overview: Описание, Дескрипшен фильма "overview"
 * @param adult: Минимальный допустимый возраст "true" для старичков и "false" для неокрепших умов
 * @param releaseDate: дата выхода фильма
 * @param video: наличие видео
 */
@Serializable
data class MovieItem(

	@SerialName("overview")
	val overview: String = "none",

	@SerialName("original_language")
	val originalLanguage: String = "none",

	@SerialName("original_title")
	val originalTitle: String = "none",

	@SerialName("video")
	val video: Boolean = false,

	@SerialName("title")
	val title: String = "none",

	@SerialName("genre_ids")
	val genreIds: List<Int?> = listOf(),

	@SerialName("poster_path")
	val posterPath: String? = null,

	@SerialName("backdrop_path")
	val backdropPath: String? = null,

	@SerialName("release_date")
	val releaseDate: String = "none",

	@SerialName("popularity")
	val popularity: Double = -1.0,

	@SerialName("vote_average")
	val voteAverage: Double = -1.0,

	@SerialName("id")
	val movieId: Long = -1,

	@SerialName("adult")
	val adult: Boolean = false,

	@SerialName("vote_count")
	val voteCount: Int = -1
)