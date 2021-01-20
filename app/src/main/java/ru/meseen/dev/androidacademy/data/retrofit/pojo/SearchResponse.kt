package ru.meseen.dev.androidacademy.data.retrofit.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(

	@SerialName("cast")
	val cast: List<CastItem>,

	@SerialName("id")
	val id: Int,

	@SerialName("crew")
	val crew: List<CrewItem>
)


