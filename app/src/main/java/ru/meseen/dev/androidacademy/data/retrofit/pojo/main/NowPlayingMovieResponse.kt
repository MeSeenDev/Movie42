package ru.meseen.dev.androidacademy.data.retrofit.pojo.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItemResponse

/**
 * @param dates период дат
 * @param page Страница
 * @param totalPages Всего страниц
 * @param results Список результатов
 * @param totalResults всего результатов на всех страницах
 * @see <a href="https://developers.themoviedb.org/3/movies/get-now-playing">API sample lists</a>
 */
@Serializable
data class NowPlayingMovieResponse(

    @SerialName("dates")
    val dates: MovieDates,

    @SerialName("page")
    val page: Int = -1,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("results")
    val results: List<MovieItemResponse>,

    @SerialName("total_results")
    val totalResults: Int
) : MovieListableWithDates {

    override fun getMovieDates(): MovieDates = dates


    override fun getMoviePage(): Int = page


    override fun getMovieTotalPages(): Int = totalPages


    override fun getMovieResults(): List<MovieItemResponse> = results


    override fun getMovieTotalResults(): Int = totalResults


}

@Serializable
data class MovieDates(

    @SerialName("maximum")
    val maximum: String = "none",

    @SerialName("minimum")
    val minimum: String = "none"
)
