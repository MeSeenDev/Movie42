package ru.meseen.dev.androidacademy.data.retrofit.pojo

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("cast")
	val cast: List<CastItem>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("crew")
	val crew: List<CrewItem>
)


