package ru.meseen.dev.androidacademy.data.base.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.MOVIE_ADDITIONAL_TABLE_NAME

@Entity(
    tableName = MOVIE_ADDITIONAL_TABLE_NAME,
    indices = [Index("genres_ids", unique = true),
        ]
)
data class MovieAdditionalDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String,

    @ColumnInfo(name = "imdb_id")
    val imdbId: String?,

    @ColumnInfo(name = "video")
    val video: Boolean,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,

    @ColumnInfo(name = "revenue")
    val revenue: Int,

    @ColumnInfo(name = "genres_ids")
    val genresIDs: String, //todo List<GenresItems>

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    /*@ColumnInfo(name = "production_countries_id")
    val productionCountriesID: Long, o*/

    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "budget")
    val budget: Int,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "original_title")
    val originalTitle: String,

    @ColumnInfo(name = "runtime")
    val runtime: Int?,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    /* @ColumnInfo(name = "spoken_languages_id")
     val spokenLanguagesID: Long,// List<SpokenLanguagesItem>*/

    /* @ColumnInfo(name = "production_companies_id")
     val productionCompaniesID: Long,// List<ProductionCompaniesItem>*/

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "tagline")
    val tagline: String?,

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "homepage")
    val homepage: String,

    @ColumnInfo(name = "status")
    val status: String
)
