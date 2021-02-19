package ru.meseen.dev.androidacademy.data.retrofit.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * @see <a href="https://developers.themoviedb.org/3/people/get-person-details">API Get the primary person details by id.</a>
 */
@Serializable
data class PersonCastResponse(

	@SerialName("also_known_as")
	val alsoKnownAs: List<String>,

	@SerialName("birthday")
	val birthday: String,

	@SerialName("gender")
	val gender: Int,

	@SerialName("imdb_id")
	val imdbId: String,

	@SerialName("known_for_department")
	val knownForDepartment: String,

	@SerialName("profile_path")
	val profilePath: String,

	@SerialName("biography")
	val biography: String,

	@SerialName("deathday")
	val deathday: String?,

	@SerialName("place_of_birth")
	val placeOfBirth: String,

	@SerialName("popularity")
	val popularity: Double,

	@SerialName("name")
	val name: String,

	@SerialName("id")
	val id: Int,

	@SerialName("adult")
	val adult: Boolean,

	@SerialName("homepage")
	val homepage: String?
)
