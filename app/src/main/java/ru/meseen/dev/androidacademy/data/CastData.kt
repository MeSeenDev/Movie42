package ru.meseen.dev.androidacademy.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
/**
 *  Дата класс с актерами
 *
 *  @param id Связывающее поле для данных в MovieEntity
 *  @param name Имя актера
 *  @param drawableUrl Адрес Фотографии актера
 *  @see <a href="https://github.com/Android-Academy-Global/fundamentals-2020-homework/blob/ex-5-coroutines/app/src/main/java/com/android/academy/fundamentals/homework/features/data/Actor.kt">ex-5-coroutines Actor</a>
 */
data class CastData(
    val id: Int = -1 /* Связывающее поле для данных в MovieEntity */,
    val name: String = "John doe" /* Имя актера */,
    val drawableUrl: String = "https://image.tmdb.org/t/p/w342/mc48QVtMhohMFrHGca8OHTB6C2B.jpg"
) : Parcelable