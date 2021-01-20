package ru.meseen.dev.androidacademy.data.base.query

interface MovieSearchQuery : SearchQuery {
    fun getMovieYear(): String
    fun getMoviePrimaryReleaseYear(): String
    fun isMovieIncludeAdult(): Boolean


}
