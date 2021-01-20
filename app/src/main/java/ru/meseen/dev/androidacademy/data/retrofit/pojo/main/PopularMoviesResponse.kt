package ru.meseen.dev.androidacademy.data.retrofit.pojo.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItem

@Serializable
data class PopularMoviesResponse(

	@SerialName("page")
	val page: Int,

	@SerialName("total_pages")
	val totalPages: Int,

	@SerialName("results")
	val results: List<MovieItem>,

	@SerialName("total_results")
	val totalResults: Int
) : MovieListable {

    override fun getMoviePage(): Int {
        return page
    }

    override fun getMovieTotalPages(): Int {
        return totalPages
    }

    override fun getMovieResults(): List<MovieItem> {
        return results
    }

    override fun getMovieTotalResults(): Int {
        return totalResults
    }
}

