package ru.meseen.dev.androidacademy.data.retrofit.pojo.items

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param id: movie ID
 * @param posterPath: Адрес изображеиня постера
 * @param voteCount: overview количество проголосовавших "votesCount"(vote_count)
 * @param title: Имя фильма Title
 * @param voteAverage: Рейтинг фильма от 0..10 "vote_average"
 * @param genres: Жанры фильма
 * @param runtime: Длительность фильма "runtime" --!!
 * @param originalLanguage: язык оригинала фильма
 * @param originalTitle: Название на оригинальном (Родном) языке
 * @param backdropPath: Адрес изображения на фоне дестрипшн фрагмента
 * @param overview: Описание, Дескрипшен фильма "overview"
 * @param adult: Минимальный допустимый возраст "true" для старичков и "false" для неокрепших умов
 * @param releaseDate: дата выхода фильма
 * @param video: наличие видео
 * @param imdbId pattern: ^tt[0-9]{7}
 */

@Serializable
data class MovieAdditionalDataResponse(

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("imdb_id")
    val imdbId: String?,

    @SerialName("video")
    val video: Boolean,

    @SerialName("title")
    val title: String,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("revenue")
    val revenue: Int,

    @SerialName("genres")
    val genres: List<GenresItem>,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountriesItem>,

    @SerialName("id")
    val id: Int,

    @SerialName("vote_count")
    val voteCount: Int,

    @SerialName("budget")
    val budget: Int,

    @SerialName("overview")
    val overview: String?,

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("runtime")
    val runtime: Int?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguagesItem>,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompaniesItem>,

    @SerialName("release_date")
    val releaseDate: String,

    @SerialName("vote_average")
    val voteAverage: Double,

    /*@SerialName("belongs_to_collection")
    val belongsToCollection: String?,*/   // Либо Null либо не понятный объект , понадобиться расроментирую

    @SerialName("tagline")
    val tagline: String?,

    @SerialName("adult")
    val adult: Boolean,

    @SerialName("homepage")
    val homepage: String,

    @SerialName("status")
    val status: String
)



