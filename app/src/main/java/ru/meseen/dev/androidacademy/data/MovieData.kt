package ru.meseen.dev.androidacademy.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.meseen.dev.androidacademy.R

@Parcelize
data class MovieData(val pg: Int = 0,
                     val labelText: String = "Default",
                     val keywordsText: String = "Default keyword",
                     val drawable: Int = R.drawable.main_movie2,
                     val reviewsStars: Int = 0,
                     val reviewsText: String = "default review",
                     val descriptionText: String = "default description",
                     val movieLength: Int = 60,
                     var isFavorite: Boolean = false,
                     val cast: List<CastData> = listOf(CastData())) : Parcelable

