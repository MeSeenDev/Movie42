package ru.meseen.dev.androidacademy.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
/**
 *  Дата класс шанров фильмов
 *
 *  @param id Связывающее поле для данных в MovieEntity
 *  @param name Название жанра фильма
 *  @see <a href="https://github.com/Android-Academy-Global/fundamentals-2020-homework/blob/ex-5-coroutines/app/src/main/java/com/android/academy/fundamentals/homework/features/data/Genre.kt"> ex-5-coroutines Genre </a>
 */
data class GenreData(
    val id: Int = -1,
    val name: String = "ФИЛЬМ ГОВНО"
) : Parcelable