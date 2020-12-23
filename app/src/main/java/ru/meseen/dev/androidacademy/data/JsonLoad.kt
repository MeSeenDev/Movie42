package ru.meseen.dev.androidacademy.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.meseen.dev.androidacademy.data.base.entity.MovieEntity
import kotlin.math.round


private val jsonFormat = Json { ignoreUnknownKeys = true }

@Serializable
private class JsonGenre(val id: Int, val name: String)

@Serializable
private class JsonActor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val drawableUrl: String
)

@Serializable
private class JsonMovie(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val actors: List<Int>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    val overview: String,
    val adult: Boolean
)

private suspend fun loadGenres(context: Context): List<GenreData> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    parseGenres(data)
}

internal fun parseGenres(data: String): List<GenreData> {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    return jsonGenres.map { GenreData(id = it.id, name = it.name) }.toList()
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

private suspend fun loadActors(context: Context): List<CastData> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    parseActors(data)
}

internal fun parseActors(data: String): List<CastData> {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    Log.wtf("TAG", "parseActors: $jsonActors")
    return jsonActors.map { CastData(id = it.id, name = it.name, drawableUrl = it.drawableUrl) }
        .toList()
}

@Suppress("unused")
internal suspend fun loadMovies(context: Context): List<MovieEntity> = withContext(Dispatchers.IO) {
    val genresMap = loadGenres(context)
    val actorsMap = loadActors(context)

    val data = readAssetFileToString(context, "data.json")
    parseMovies(data, genresMap, actorsMap)
}

internal fun parseMovies(
    data: String,
    genreData: List<GenreData>,
    actors: List<CastData>
): List<MovieEntity> {
    val genresMap = genreData.associateBy { it.id }
    val actorsMap = actors.associateBy { it.id }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    return jsonMovies.map { jsonMovie ->
        @Suppress("unused")
        (MovieEntity(
            _id = jsonMovie.id.toLong(),
            pgRating = if (jsonMovie.adult) 16 else 13,
            labelText = jsonMovie.title,
            descriptionText = jsonMovie.overview,
            posterIMG = jsonMovie.posterPicture,
            backdropIMG = jsonMovie.backdropPicture,
            ratings = round(jsonMovie.ratings / 2),
            reviewsText = jsonMovie.votesCount,
            movieLength = jsonMovie.runtime,
            genreData = jsonMovie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            },
            cast = jsonMovie.actors.map {
                actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
            }
        ))
    }
}