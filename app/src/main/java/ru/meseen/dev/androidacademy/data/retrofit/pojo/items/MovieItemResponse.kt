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
 * @param originalLanguage: язык оригинала фильма
 * @param originalTitle: Название на оригинальном (Родном) языке
 * @param backdropPath: Адрес изображения на фоне дестрипшн фрагмента
 * @param overview: Описание, Дескрипшен фильма "overview"
 * @param adult: Минимальный допустимый возраст "true" для старичков и "false" для неокрепших умов
 * @param releaseDate: дата выхода фильма
 * @param video: наличие видео
 * @see <a href="https://developers.themoviedb.org/3/movies/get-top-rated-movies">API example List</a>
 */
@Serializable
data class MovieItemResponse(

	@SerialName("overview")
	val overview: String ,

	@SerialName("original_language")
	val originalLanguage: String,

	@SerialName("original_title")
	val originalTitle: String ,

	@SerialName("video")
	val video: Boolean = false,

	@SerialName("title")
	val title: String = "none",

	@SerialName("genre_ids")
	val genreIds: List<Int?> = listOf(),

	@SerialName("poster_path")
	val posterPath: String?,

	@SerialName("backdrop_path")
	val backdropPath: String?,

	@SerialName("release_date")
	val releaseDate: String,

	@SerialName("popularity")
	val popularity: Double,

	@SerialName("vote_average")
	val voteAverage: Double,

	@SerialName("id")
	val movieId: Long,

	@SerialName("adult")
	val adult: Boolean,

	@SerialName("vote_count")
	val voteCount: Int
)