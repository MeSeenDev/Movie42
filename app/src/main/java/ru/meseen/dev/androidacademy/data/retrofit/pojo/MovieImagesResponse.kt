package ru.meseen.dev.androidacademy.data.retrofit.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * Querying images with a language parameter will filter the results. If you want to include a fallback language (especially useful for backdrops)
 * you can use the include_image_language parameter. This should be a comma seperated value like so: include_image_language=en,null
 * @see <a href="https://developers.themoviedb.org/3/movies/get-movie-images">API Get the images that belong to a movie</a>
 */
@Serializable
data class MovieImagesResponse(

	@SerialName("backdrops")
	val backdrops: List<BackdropsItem>,

	@SerialName("posters")
	val posters: List<PostersItem>,

	@SerialName("id")
	val movieIDs: Int
)

/**
 * @param height высота изображения
 * @param width ширина изображения
 * @param aspectRatio соотношеине сторон
 * @param voteAverage рейтинг изображения
 * @param voteCount количество проголосовавших
 * @param imageFilePath путь, чать ссылки на изображение (backdrop_sizes)
 * @param language iso_639_1 язык пример ru , en , null
 */
@Serializable
data class BackdropsItem(

	@SerialName("aspect_ratio")
	val aspectRatio: Double = 0.0,

	@SerialName("file_path")
	val imageFilePath: String = "none",

	@SerialName("vote_average")
	val voteAverage: Double = 0.0,

	@SerialName("width")
	val width: Int = -1,

	@SerialName("iso_639_1")
	val language: String? = null,

	@SerialName("vote_count")
	val voteCount: Int = -1,

	@SerialName("height")
	val height: Int = -1
)

/**
 * @param height высота изображения
 * @param width ширина изображения
 * @param aspectRatio соотношеине сторон
 * @param voteAverage рейтинг изображения
 * @param voteCount количество проголосовавших
 * @param imageFilePath путь, чать ссылки на изображение
 * @param language iso_639_1 язык пример ru , en , null
 */
@Serializable
data class PostersItem(

	@SerialName("aspect_ratio")
	val aspectRatio: Double = 0.0,

	@SerialName("file_path")
	val imageFilePath: String = "none",

	@SerialName("vote_average")
	val voteAverage: Double = 0.0,

	@SerialName("width")
	val width: Int = -1,

	@SerialName("iso_639_1")
	val language: String = "none",

	@SerialName("vote_count")
	val voteCount: Int = -1,

	@SerialName("height")
	val height: Int = -1
)
