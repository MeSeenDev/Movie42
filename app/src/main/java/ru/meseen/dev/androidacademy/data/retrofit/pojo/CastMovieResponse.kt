package ru.meseen.dev.androidacademy.data.retrofit.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 *
 *@see <a href="https://developers.themoviedb.org/3/people/get-person-movie-credits">API Get the movie credits for a person.</a>
 */
@Serializable
data class CastMovieResponse(

	@SerialName("cast")
	val cast: List<CastItem>,

	@SerialName("id")
	val id: Int,

	@SerialName("crew")
	val crew: List<CrewItem>
)

@Serializable
data class CastItem(

	@SerialName("cast_id")
	val castId: Int,

	@SerialName("character")
	val character: String,

	@SerialName("gender")
	val gender: Int?,

	@SerialName("credit_id")
	val creditId: String,

	@SerialName("known_for_department")
	val knownForDepartment: String,

	@SerialName("original_name")
	val originalName: String,

	@SerialName("popularity")
	val popularity: Double,

	@SerialName("name")
	val name: String,

	@SerialName("profile_path")
	val profilePath: String?,

	@SerialName("id")
	val id: Int,

	@SerialName("adult")
	val adult: Boolean,

	@SerialName("order")
	val order: Int
)

@Serializable
data class CrewItem(

	@SerialName("gender")
	val gender: Int?,

	@SerialName("credit_id")
	val creditId: String,

	@SerialName("known_for_department")
	val knownForDepartment: String,

	@SerialName("original_name")
	val originalName: String,

	@SerialName("popularity")
	val popularity: Double,

	@SerialName("name")
	val name: String,

	@SerialName("profile_path")
	val profilePath: String?,

	@SerialName("id")
	val id: Int,

	@SerialName("adult")
	val adult: Boolean,

	@SerialName("department")
	val department: String,

	@SerialName("job")
	val job: String
)
