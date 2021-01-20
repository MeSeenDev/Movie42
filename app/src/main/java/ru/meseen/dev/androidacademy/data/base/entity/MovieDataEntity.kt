package ru.meseen.dev.androidacademy.data.base.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.TABLE_NAME
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItem

/**
 * @param movieId: movie ID
 * @param posterPath: Адрес изображеиня постера
 * @param voteCount: overview количество проголосовавших "votesCount"(vote_count)
 * @param title: Имя фильма Title
 * @param voteAverage: Рейтинг фильма от 0..10 "vote_average"
 * runtime: Длительность фильма "runtime" --!!
 * @param originalLanguage: язык оригинала фильма
 * @param originalTitle: Название на оригинальном (Родном) языке
 * @param backdropPath: Адрес изображения на фоне дестрипшн фрагмента
 * @param overview: Описание, Дескрипшен фильма "overview"
 * @param adult: Минимальный допустимый возраст "true" для старичков и "false" для неокрепших умов
 * @param releaseDate: дата выхода фильма
 * @param video: наличие видео
 */
@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
data class MovieDataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    val _id: Long? = null,
    val overview: String = "none",
    val originalLanguage: String = "none",
    val originalTitle: String = "none",
    val video: Boolean,
    val title: String = "none",
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String = "none",
    val popularity: Double,
    val voteAverage: Double,
    val genreIds: String,
    @ColumnInfo(name = "id")
    val movieId: Long = -1,
    val adult: Boolean = false,
    val voteCount: Int = -100,
    var isFavorite: Boolean = false,
    val listType: String

) {

    constructor(movieItem: MovieItem, listType: String, genres: String) : this(
        _id = null,
        overview = movieItem.overview,
        originalLanguage = movieItem.originalLanguage,
        originalTitle = movieItem.originalTitle,
        video = movieItem.video,
        title = movieItem.title,
        posterPath = movieItem.posterPath,
        backdropPath = movieItem.backdropPath,
        releaseDate = movieItem.releaseDate,
        popularity = movieItem.popularity,
        voteAverage = movieItem.voteAverage,
        genreIds = genres,
        movieId = movieItem.movieId,
        adult = movieItem.adult,
        voteCount = movieItem.voteCount,
        isFavorite = false,
        listType = listType
    )

}

