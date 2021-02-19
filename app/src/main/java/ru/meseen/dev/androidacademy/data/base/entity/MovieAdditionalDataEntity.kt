package ru.meseen.dev.androidacademy.data.base.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.MOVIE_ADDITIONAL_TABLE_NAME
import ru.meseen.dev.androidacademy.data.repositories.impl.Repository
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieAdditionalDataResponse


/**
 * @param id: movie ID
 * @param posterPath: Адрес изображеиня постера
 * @param voteCount: overview количество проголосовавших "votesCount"(vote_count)
 * @param title: Имя фильма Title
 * @param voteAverage: Рейтинг фильма от 0..10 "vote_average"
 * @param genresIDs: Жанры фильма
 * @param runtime: Длительность фильма "runtime" --!!
 * @param originalLanguage: язык оригинала фильма
 * @param originalTitle: Название на оригинальном (Родном) языке
 * @param backdropPath: Адрес изображения на фоне дестрипшн фрагмента
 * @param overview: Описание, Дескрипшен фильма "overview"
 * @param adult: Минимальный допустимый возраст "true" для старичков и "false" для неокрепших умов
 * @param releaseDate: дата выхода фильма
 * @param homepage: Домашняя страница на некотором сайте к примеру КиноПоиск
 * @param video: наличие видео
 * @param runtime: Длительность фильма
 * @param status: Вышел ли фильм или на стадии производства
 * @param imdbId pattern: ^tt[0-9]{7}
 * @see <a href="https://developers.themoviedb.org/3/movies/get-movie-details">API get Movie Details</a>
 */
@Entity(
    tableName = MOVIE_ADDITIONAL_TABLE_NAME,
)
data class MovieAdditionalDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String,

    @ColumnInfo(name = "imdb_id")
    val imdbId: String?,

    @ColumnInfo(name = "video")
    val video: Boolean,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,

    @ColumnInfo(name = "revenue")
    val revenue: Int,

    @ColumnInfo(name = "genres_ids")
    val genresIDs: String,

    @ColumnInfo(name = "cast_ids")
    val castIDs: String,

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    @ColumnInfo(name = "favorites")
    var isFavorites: Boolean = false,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "budget")
    val budget: Int,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "original_title")
    val originalTitle: String,

    @ColumnInfo(name = "runtime")
    val runtime: Int?,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "tagline")
    val tagline: String?,

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "homepage")
    val homepage: String,

    @ColumnInfo(name = "status")
    val status: String
) {
    constructor(movie: MovieAdditionalDataResponse, genresIDs: String) : this(
        id = movie.id,
        originalLanguage = movie.originalLanguage,
        imdbId = movie.imdbId,
        video = movie.video,
        title = movie.title,
        backdropPath = movie.backdropPath,
        revenue = movie.revenue,
        genresIDs = movie.genres.map { it.genresId }.joinToString(),
        castIDs = genresIDs,
        popularity = movie.popularity,
        voteCount = movie.voteCount,
        budget = movie.budget,
        overview = movie.overview,
        originalTitle = movie.originalTitle,
        runtime = movie.runtime,
        posterPath = movie.posterPath,
        releaseDate = movie.releaseDate,
        voteAverage = movie.voteAverage,
        tagline = movie.tagline,
        adult = movie.adult,
        homepage = movie.homepage,
        status = movie.status
    )

}
