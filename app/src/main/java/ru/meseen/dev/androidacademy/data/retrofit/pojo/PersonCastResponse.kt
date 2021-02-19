package ru.meseen.dev.androidacademy.data.retrofit.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * @see <a href="https://developers.themoviedb.org/3/people/get-person-details">API Get the primary person details by id.</a>
 */
@Serializable
data class PersonCastResponse(

	@SerialName("also_known_as")
	val alsoKnownAs: List<String> = listOf(),

	@SerialName("birthday")
	val birthday: String = "none",

	@SerialName("gender")
	val gender: Int = -1,

	@SerialName("imdb_id")
	val imdbId: String = "none",

	@SerialName("known_for_department")
	val knownForDepartment: String = "none",

	@SerialName("profile_path")
	val profilePath: String = "none",

	@SerialName("biography")
	val biography: String = "none",

	@SerialName("deathday")
	val deathday: String? = null,

	@SerialName("place_of_birth")
	val placeOfBirth: String = "none",

	@SerialName("popularity")
	val popularity: Double = 0.0,

	@SerialName("name")
	val name: String = "none",

	@SerialName("id")
	val id: Int = -1,

	@SerialName("adult")
	val adult: Boolean = false,

	@SerialName("homepage")
	val homepage: String? = null
)
