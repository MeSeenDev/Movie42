package ru.meseen.dev.androidacademy.data.retrofit.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.meseen.dev.androidacademy.data.retrofit.pojo.CastMovieResponse
import ru.meseen.dev.androidacademy.data.retrofit.pojo.PersonCastResponse
import ru.meseen.dev.androidacademy.data.retrofit.pojo.SearchMoviesResponse
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.GenresItems
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieAdditionalDataResponse
import ru.meseen.dev.androidacademy.data.retrofit.pojo.main.BaseListMovies

interface MovieService {


    @GET("search/{path}")
    suspend fun searchMovies(
        @Path("path") searchType: String,
        @Query("language") language: String = "en-US",
        @Query("region") region: String = "US",
        @Query("page") page: Int = 1,
        @Query("year") year: String = "",
        @Query("primary_release_year") primaryReleaseYear: String = "",
        @Query("include_adult") include_adult: Boolean = true,
        @Query("query") query: String
    ): SearchMoviesResponse


    /**
     * @param listType Тип запрашиваемого списка top_rated, now_playing, popular, upcoming
     * @param language Язык реультата
     * @param page Страница с фильмами
     * @param region ISO 3166-1 региональный код для персонализации запросов
     */
    @GET("movie/{listType}")
    suspend fun loadList(
        @Path("listType") listType: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String = "US"
    ): BaseListMovies

    /**
     * @param movie_id  Идентификатор фильма на themoviedb.org
     * @param language Язык ответа
     * @param appendToResponse  Дополнительное получение videos,images или и того ио другово в доп массивах с учетом языка
     */
    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movie_id: String,
        @Query("language") language: String = "en-US",
        @Query("append_to_response") appendToResponse: String = ""
    ): MovieAdditionalDataResponse


    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCastById(
        @Path("movie_id") movie_id: String,
        @Query("language") language: String = "en-US",
    ): CastMovieResponse


    @GET("person/{person_id}")
    suspend fun getCastPersonById(
        @Path("person_id") person_id: String,
        @Query("language") language: String = "en-US"
    ): PersonCastResponse


    @GET("genre/movie/list")
    suspend fun getGenresList(
        @Query("language") language: String = "en-US"
    ): GenresItems

    @GET("configuration")
    suspend fun getConfig(): ConfigurationResponse




}