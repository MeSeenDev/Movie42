package ru.meseen.dev.androidacademy.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import ru.meseen.dev.androidacademy.R

@Parcelize
data class CastData(
    val name: String = "John doe",
    val drawable: Int = R.drawable.movie
) :
    Parcelable