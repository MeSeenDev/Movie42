package ru.meseen.dev.androidacademy.data.base.query

import java.io.Serializable

interface MovieSearchQuery : SearchQuery , Serializable {
    fun getMovieYear(): String
    fun getMoviePrimaryReleaseYear(): String
    fun isMovieIncludeAdult(): Boolean


}
