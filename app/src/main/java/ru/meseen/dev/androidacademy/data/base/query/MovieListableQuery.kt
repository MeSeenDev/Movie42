package ru.meseen.dev.androidacademy.data.base.query

interface MovieListableQuery : BaseQuery {
    fun getMoviePath(): String
    fun getMoviesPage(): Int
    fun getMoviesRegion(): String
}


