package ru.meseen.dev.androidacademy.data.base.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import ru.meseen.dev.androidacademy.data.CastData
import ru.meseen.dev.androidacademy.data.GenreData
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
@Parcelize
/**
 *  Основной Дата класс с Фильмами
 *
 *@param _id: db _id Автогенерируемый
 *@param pgRating: Минимальный допустимый возраст "minimumAge"
 *@param labelText: Имя фильма Title
 *@param genreData: Жанры фильма
 *@param posterIMG: Адрес изображеиня постера
 *@param backdropIMG: Адрес изображения на фоне дестрипшн фрагмента
 *@param ratings: Рейтинг фильма от 0..10 "vote_average"
 *@param reviewsText: overview количество проголосовавших "votesCount"(vote_count)
 *@param descriptionText: Описание, Дескрипшен фильма "overview"
 *@param movieLength: Длительность фильма "runtime"
 *@param isFavorite: Добавлен ли в избранное
 *@param cast: Список Актеров каста "actors"
 *@see <a href="https://github.com/Android-Academy-Global/fundamentals-2020-homework/blob/ex-5-coroutines"> ex-5-coroutines </a>
 */
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0,
    val pgRating: Int = 0/* Минимальный допустимый возраст */,
    val labelText: String = "No data" /* Имя фильма Title */,
    val genreData: List<GenreData> = listOf(GenreData()) /* Жанры фильма */,
    val posterIMG: String = "https://image.tmdb.org/t/p/w342/riYInlsq2kf1AWoGm80JQW5dLKp.jpg",
    val backdropIMG: String = "https://image.tmdb.org/t/p/w342/mc48QVtMhohMFrHGca8OHTB6C2B.jpg",
    val ratings: Float = 0f /* Рейтинг фильма от 0..10 vote_average */,
    val reviewsText: Int = 101 /* overview количество проголосовавших votesCount */,
    val descriptionText: String = " no description"/* Описание, Дескрипшен фильма overview */,
    val movieLength: Int = 60 /* Длительность фильма runtime */,
    var isFavorite: Boolean = false,
    val cast: List<CastData> = listOf(CastData()) /* Список Актеров каста actors */
) : Parcelable {

    //то что ходит как собака, ведет себя как собака и лает как собака является собакой
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieEntity

        if (labelText != other.labelText) return false
        if (descriptionText != other.descriptionText) return false

        return true
    }

    override fun hashCode(): Int {
        var result = labelText.hashCode()
        result = 31 * result + descriptionText.hashCode()
        return result
    }
}

