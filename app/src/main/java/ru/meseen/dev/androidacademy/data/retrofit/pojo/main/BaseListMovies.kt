package ru.meseen.dev.androidacademy.data.retrofit.pojo.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItemResponse



/**
 * @param page Страница
 * @param totalPages Всего страниц
 * @param results Список результатов
 * @param totalResults всего результатов на всех страницах
 * @see <a href="https://developers.themoviedb.org/3/movies/get-top-rated-movies">API sample lists</a>
 */
@Serializable
class BaseListMovies(
    @SerialName("page")
    val page: Int = -1,

    @SerialName("total_pages")
    val totalPages: Int = -1,

    @SerialName("results")
    val results: List<MovieItemResponse> = listOf(),

    @SerialName("total_results")
    val totalResults: Int = -1
) : MovieListable {

    override fun getMoviePage(): Int = page


    override fun getMovieTotalPages(): Int = totalPages


    override fun getMovieResults(): List<MovieItemResponse> = results


    override fun getMovieTotalResults(): Int = totalResults

}