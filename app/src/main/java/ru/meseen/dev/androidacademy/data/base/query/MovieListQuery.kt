package ru.meseen.dev.androidacademy.data.base.query

class MovieListQuery(
    private val path: String,
    private val page: Int,
    private val region: String,
    private val language: String
) : MovieListableQuery {


    override fun getMoviePath(): String = path

    override fun getMoviesPage(): Int = page

    override fun getMoviesRegion(): String = region

    override fun getMoviesLanguage(): String = language
}