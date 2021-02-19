package ru.meseen.dev.androidacademy.data.base.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.TABLE_NAME
import ru.meseen.dev.androidacademy.data.repositories.impl.Repository
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItemResponse

/**
 * @param movieId: movie ID
 * @param posterPath: Адрес изображеиня постера
 * @param voteCount: overview количество проголосовавших "votesCount"(vote_count)
 * @param title: Имя фильма Title
 * @param voteAverage: Рейтинг фильма от 0..10 "vote_average"
 * @param runtime: Длительность фильма "runtime" --!!
 * @param originalLanguage: язык оригинала фильма
 * @param originalTitle: Название на оригинальном (Родном) языке
 * @param backdropPath: Адрес изображения на фоне дестрипшн фрагмента
 * @param overview: Описание, Дескрипшен фильма "overview"
 * @param adult: Минимальный допустимый возраст "true" для старичков и "false" для неокрепших умов
 * @param releaseDate: дата выхода фильма
 * @param video: наличие видео
 * @param runtime: Длительность фильма если есть
 * @param isFavorite: Является ли фильм избранным
 * @param listType: тип одного из листов гравного экрани и экрани поиска см
 * @see Repository.ListType enum
 * @see <a href="https://developers.themoviedb.org/3/movies/get-top-rated-movies">API </a>
 */
@Entity(tableName = TABLE_NAME)
data class MovieDataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    val _id: Long? = null,

    @ColumnInfo(name = "id")
    val movieId: Long,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "originalLanguage")
    val originalLanguage: String,

    @ColumnInfo(name = "originalTitle")
    val originalTitle: String,

    @ColumnInfo(name = "video")
    val video: Boolean,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "posterPath")
    val posterPath: String?,

    @ColumnInfo(name = "backdropPath")
    val backdropPath: String?,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,

    @ColumnInfo(name = "genreIds")
    val genreIds: String,

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "voteCount")
    val voteCount: Int,

    @ColumnInfo(name = "runtime")
    val runtime: Int?,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "listType")
    val listType: String


) {
    constructor(
        movieItemResponse: MovieItemResponse,
        listType: String,
        genres: String,
        runtime: Int? = null
    ) : this(
        _id = null,
        overview = movieItemResponse.overview,
        originalLanguage = movieItemResponse.originalLanguage,
        originalTitle = movieItemResponse.originalTitle,
        video = movieItemResponse.video,
        title = movieItemResponse.title,
        posterPath = movieItemResponse.posterPath,
        backdropPath = movieItemResponse.backdropPath,
        releaseDate = movieItemResponse.releaseDate,
        popularity = movieItemResponse.popularity,
        voteAverage = movieItemResponse.voteAverage,
        genreIds = genres,
        movieId = movieItemResponse.movieId,
        adult = movieItemResponse.adult,
        voteCount = movieItemResponse.voteCount,
        runtime = runtime,
        isFavorite = false,
        listType = listType
    )


}

