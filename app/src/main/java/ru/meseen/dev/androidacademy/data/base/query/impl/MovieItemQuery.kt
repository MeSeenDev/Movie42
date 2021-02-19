package ru.meseen.dev.androidacademy.data.base.query.impl

import ru.meseen.dev.androidacademy.data.base.query.MovieByIdQuery

/**
 * @param movieIDs  ID получаемого фильма
 * @param appendToResponse дополнение к запросу videos,images
 * @param language Язык ответа (Название описание)
 * @see <a href="https://developers.themoviedb.org/3/movies/get-movie-details">API get Movie Details</a>
 */
class MovieItemQuery(
    private val movieIDs: Int,
    private val appendToResponse: String = "",
    private val language: String
) : MovieByIdQuery {

    override fun getMovieID(): Int = movieIDs

    override fun getAppendToResponse(): String = appendToResponse

    override fun getMoviesLanguage(): String = language
}