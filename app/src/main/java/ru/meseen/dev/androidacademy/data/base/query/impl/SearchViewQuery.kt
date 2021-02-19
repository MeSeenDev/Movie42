package ru.meseen.dev.androidacademy.data.base.query.impl

import ru.meseen.dev.androidacademy.data.base.query.MovieSearchQuery
import java.io.Serializable
/**
 * @param path: Тип искомого к примеру
 * (company, collection, keyword, movie, multi, person, tv)
 * @param page: номер страницы запроса
 * @param region: дополнение к запросу videos,images ISO 3166-1
 * @param year: год выхода
 * @param primary_release_year: первоначальный год выпуска
 * @param include_adult: включить фильмы для взрослых
 * @param query: поисковый запрос минимум 1 буква
 * @param language: Язык ответа (Название описание ISO 3166-1)
 * @see <a href="https://developers.themoviedb.org/3/search/search-movies">API Search</a>
 * @see <a href="https://developers.themoviedb.org/3/getting-started/search-and-query-for-details">Search & Query For Details</a>
 */
data class SearchViewQuery(
    private val path: String = "movie",
    private val page: Int = 1,
    private val region: String = "",
    private val year: String = "",
    private val primary_release_year: String = "",
    private val include_adult: Boolean = false,
    private val language: String = "",
    private val query: String
) : MovieSearchQuery, Serializable {

    override fun getSearchQuery(): String = query

    override fun getMovieYear(): String = year

    override fun getMoviePrimaryReleaseYear(): String = primary_release_year

    override fun isMovieIncludeAdult(): Boolean = include_adult

    override fun getMoviePath(): String = path

    override fun getMoviesPage(): Int = page

    override fun getMoviesRegion(): String = region

    override fun getMoviesLanguage(): String = language
}