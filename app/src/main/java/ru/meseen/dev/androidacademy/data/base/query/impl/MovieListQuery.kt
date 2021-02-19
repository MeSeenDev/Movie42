package ru.meseen.dev.androidacademy.data.base.query.impl

import ru.meseen.dev.androidacademy.data.base.query.MovieListableQuery



/**
 * @param path Тип искомого к примеру
 * (company, collection, keyword, movie, multi, person, tv)
 * @param page номер страницы запроса
 * @param region дополнение к запросу videos,images
 * @param language Язык ответа (Название описание)
 * @see <a href="https://developers.themoviedb.org/3/search/search-movies">API Search</a>
 * @see <a href="https://developers.themoviedb.org/3/getting-started/search-and-query-for-details">Search & Query For Details</a>
 */
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