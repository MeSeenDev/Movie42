package ru.meseen.dev.androidacademy.data.retrofit.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItemResponse

/**
 * @see <a href="https://developers.themoviedb.org/3/getting-started/search-and-query-for-details">Search & Query For Details</a>
 * @see <a href="https://developers.themoviedb.org/3/search/search-movies">API Search example</a>
 */
@Serializable
data class SearchMoviesResponse(

    @SerialName("page")
    val page: Int = 1,

    @SerialName("total_pages")
    val totalPages: Int = 0,

    @SerialName("results")
    val results: List<MovieItemResponse> = listOf(),

    @SerialName("total_results")
    val totalResults: Int = 0
)


