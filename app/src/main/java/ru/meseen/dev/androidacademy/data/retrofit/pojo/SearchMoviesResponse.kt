package ru.meseen.dev.androidacademy.data.retrofit.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItem

@Serializable
data class SearchMoviesResponse(

	@SerialName("page")
	val page: Int,

	@SerialName("total_pages")
	val totalPages: Int,

	@SerialName("results")
	val results: List<MovieItem>,

	@SerialName("total_results")
	val totalResults: Int
)


