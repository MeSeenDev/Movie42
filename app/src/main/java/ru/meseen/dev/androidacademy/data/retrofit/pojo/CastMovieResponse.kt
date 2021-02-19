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
	val castId: Int = -1,

	@SerialName("character")
	val character: String = "none",

	@SerialName("gender")
	val gender: Int? = null,

	@SerialName("credit_id")
	val creditId: String = "none",

	@SerialName("known_for_department")
	val knownForDepartment: String = "none",

	@SerialName("original_name")
	val originalName: String = "none",

	@SerialName("popularity")
	val popularity: Double = 0.0,

	@SerialName("name")
	val name: String = "none",

	@SerialName("profile_path")
	val profilePath: String? = "none",

	@SerialName("id")
	val id: Int = -1,

	@SerialName("adult")
	val adult: Boolean = false,

	@SerialName("order")
	val order: Int = -1
)

@Serializable
data class CrewItem(

	@SerialName("gender")
	val gender: Int? = null,

	@SerialName("credit_id")
	val creditId: String = "none",

	@SerialName("known_for_department")
	val knownForDepartment: String = "none",

	@SerialName("original_name")
	val originalName: String = "none",

	@SerialName("popularity")
	val popularity: Double = 0.0,

	@SerialName("name")
	val name: String = "none",

	@SerialName("profile_path")
	val profilePath: String? = null,

	@SerialName("id")
	val id: Int = -1,

	@SerialName("adult")
	val adult: Boolean = false,

	@SerialName("department")
	val department: String = "none",

	@SerialName("job")
	val job: String = "none"
)
