package ru.meseen.dev.androidacademy.data.retrofit.pojo.main

import com.google.gson.annotations.SerializedName
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItem

data class NowPlayingMovieResponse(

    @field:SerializedName("dates")
        val dates: MovieDates,

    @field:SerializedName("page")
        val page: Int = -1,

    @field:SerializedName("total_pages")
        val totalPages: Int,

    @field:SerializedName("results")
        val results: List<MovieItem>,

    @field:SerializedName("total_results")
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

data class MovieDates(

        @field:SerializedName("maximum")
        val maximum: String,

        @field:SerializedName("minimum")
        val minimum: String
)
