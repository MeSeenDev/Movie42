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
 * @see <a href="https://developers.themoviedb.org/3/movies/get-upcoming">API sample lists</a>
 */
@Serializable
data class UpComingMoviesResponse(

    @SerialName("dates")
	val dates: MovieDates,

    @SerialName("page")
	val page: Int,

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




