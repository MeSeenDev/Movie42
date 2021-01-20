package ru.meseen.dev.androidacademy.data.retrofit.pojo.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItem

@Serializable
class BaseListMovies(
    @SerialName("page")
    val page: Int,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("results")
    val results: List<MovieItem>,

    @SerialName("total_results")
    val totalResults: Int
) : MovieListable {

    override fun getMoviePage(): Int = page


    override fun getMovieTotalPages(): Int = totalPages


    override fun getMovieResults(): List<MovieItem> = results


    override fun getMovieTotalResults(): Int = totalResults

}