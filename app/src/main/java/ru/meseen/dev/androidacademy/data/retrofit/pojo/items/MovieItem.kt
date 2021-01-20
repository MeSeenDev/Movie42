package ru.meseen.dev.androidacademy.data.retrofit.pojo.items

import com.google.gson.annotations.SerializedName

/**
 * @param id: movie ID
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
data class MovieItem(

		@field:SerializedName("overview")
		val overview: String = "none",

		@field:SerializedName("original_language")
		val originalLanguage: String= "none",

		@field:SerializedName("original_title")
		val originalTitle: String= "none",

		@field:SerializedName("video")
		val video: Boolean,

		@field:SerializedName("title")
		val title: String = "none",

		@field:SerializedName("genre_ids")
		val genreIds: List<Int?>,

		@field:SerializedName("poster_path")
		val posterPath: String= "none",

		@field:SerializedName("backdrop_path")
		val backdropPath: String = "none",

		@field:SerializedName("release_date")
		val releaseDate: String = "none",

		@field:SerializedName("popularity")
		val popularity: Double,

		@field:SerializedName("vote_average")
		val voteAverage: Double,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("adult")
		val adult: Boolean,

		@field:SerializedName("vote_count")
		val voteCount: Int
)