package ru.meseen.dev.androidacademy.data.base.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.TABLE_NAME
import ru.meseen.dev.androidacademy.data.repositories.impl.Repository
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItemResponse
import ru.meseen.dev.androidacademy.workers.notifications.Notifiable

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
 * @param description: Описание, Дескрипшен фильма "overview"
 * @param adult: Минимальный допустимый возраст "true" для старичков и "false" для неокрепших умов
 * @param releaseDate: дата выхода фильма
 * @param video: наличие видео
 * @param runtime: Длительность фильма если есть
 * @param isFavorite: Является ли фильм избранным
 * @param listType: тип одного из листов гравного экрани и экрани поиска см
 * @see [ru.meseen.dev.androidacademy.support.ListType] enum
 * @see <a href="https://developers.themoviedb.org/3/movies/get-top-rated-movies">API </a>
 */
@Entity(tableName = TABLE_NAME)
data class MovieDataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    val _id: Long? = null,

    @ColumnInfo(name = "id")
    override val movieId: Long,

    @ColumnInfo(name = "overview")
    override val description: String,

    @ColumnInfo(name = "originalLanguage")
    val originalLanguage: String,

    @ColumnInfo(name = "originalTitle")
    val originalTitle: String,

    @ColumnInfo(name = "video")
    val video: Boolean,

    @ColumnInfo(name = "title")
    override val title: String,

    @ColumnInfo(name = "posterPath")
    override val posterPath: String?,

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


) : Notifiable(
    movieId = movieId,
    title = title,
    description = description,
    posterPath = posterPath
) {
    constructor(
        movieItemResponse: MovieItemResponse,
        listType: String,
        genres: String,
        runtime: Int? = null
    ) : this(
        _id = null,
        description = movieItemResponse.overview,
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieDataEntity

        if (movieId != other.movieId) return false
        if (description != other.description) return false
        if (originalLanguage != other.originalLanguage) return false
        if (originalTitle != other.originalTitle) return false
        if (video != other.video) return false
        if (title != other.title) return false
        if (posterPath != other.posterPath) return false
        if (backdropPath != other.backdropPath) return false
        if (releaseDate != other.releaseDate) return false

        if (popularity.toString() != other.popularity.toString()) return false
        if (voteAverage.toString() != other.voteAverage.toString()) return false
        if (genreIds != other.genreIds) return false
        if (adult != other.adult) return false
        if (voteCount != other.voteCount) return false


        return true
    }

    override fun hashCode(): Int {
        var result = movieId.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + originalLanguage.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + video.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + releaseDate.hashCode()

        result = 31 * result + popularity.toString().hashCode()
        result = 31 * result + voteAverage.toString().hashCode()
        result = 31 * result + genreIds.hashCode()
        result = 31 * result + adult.hashCode()
        result = 31 * result + voteCount
        return result
    }


}

