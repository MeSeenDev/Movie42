package ru.meseen.dev.androidacademy.data.retrofit.pojo.main

import com.google.gson.annotations.SerializedName
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItem

data class TopRatedMoviesResponse(

    @field:SerializedName("page")
    val page: Int = -1,

    @field:SerializedName("total_pages")
    val totalPages: Int = -1,

    @field:SerializedName("results")
    val results: List<MovieItem> = listOf(),

    @field:SerializedName("total_results")
    val totalResults: Int = -1
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


