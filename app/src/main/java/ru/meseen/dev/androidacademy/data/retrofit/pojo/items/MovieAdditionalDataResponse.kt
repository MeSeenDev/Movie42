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
 * @param runtime: Длительность фильма
 * @param imdbId pattern: ^tt[0-9]{7}
 * @see <a href="https://developers.themoviedb.org/3/movies/get-movie-details">API get Movie Details</a>
 */

@Serializable
data class MovieAdditionalDataResponse(

    @SerialName("original_language")
    val originalLanguage: String = "none",

    @SerialName("imdb_id")
    val imdbId: String? = null,

    @SerialName("video")
    val video: Boolean = false,

    @SerialName("title")
    val title: String = "none",

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("revenue")
    val revenue: Long = -1,

    @SerialName("genres")
    val genres: List<GenresItem> = listOf(),

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountriesItem> = listOf(),

    @SerialName("id")
    val id: Int = -1,

    @SerialName("vote_count")
    val voteCount: Int = -1,

    @SerialName("budget")
    val budget: Int = -1,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("original_title")
    val originalTitle: String = "none",

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguagesItem> = listOf(),

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompaniesItem> = listOf(),

    @SerialName("release_date")
    val releaseDate: String = "none",

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    /*@SerialName("belongs_to_collection")
    val belongsToCollection: String?,*/   // Либо Null либо не понятный объект , понадобиться расроментирую

    @SerialName("tagline")
    val tagline: String? = null,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("homepage")
    val homepage: String = "none",

    @SerialName("status")
    val status: String = "none"
)



