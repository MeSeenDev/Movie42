package ru.meseen.dev.androidacademy.data.retrofit.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient.API_KEY
import ru.meseen.dev.androidacademy.data.retrofit.pojo.CastMovieResponse
import ru.meseen.dev.androidacademy.data.retrofit.pojo.PersonCastResponse
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieAdditionalDataItem
import ru.meseen.dev.androidacademy.data.retrofit.pojo.main.NowPlayingMovieResponse
import ru.meseen.dev.androidacademy.data.retrofit.pojo.main.PopularMoviesResponse
import ru.meseen.dev.androidacademy.data.retrofit.pojo.main.TopRatedMoviesResponse

interface MovieService {


    @GET("/search/movie?api_key=$API_KEY")
    fun searchMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("year") year: String = "",
        @Query("primary_release_year") primaryReleaseYear: String = "",
        @Query("include_adult") include_adult: Boolean = true,
        @Query("query") query: String
    )

    //@GET("movie/top_rated?api_key=$API_KEY")
    @GET("movie/top_rated?api_key=$API_KEY")
    fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String = ""
    ): Call<TopRatedMoviesResponse>


    @GET("movie/now_playing?api_key=$API_KEY")
    fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String = ""
    ): Call<NowPlayingMovieResponse>


    @GET("movie/popular?api_key=$API_KEY")
    fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String = ""
    ): Call<PopularMoviesResponse>


    @GET("movie/upcoming?api_key=$API_KEY")
    fun getUpComing(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String = ""
    ): Call<PopularMoviesResponse>


    @GET("movie/{movie_id}?api_key=$API_KEY")
    fun getMovieById(
        @Path("movie_id") movie_id: String,
        @Query("language") language: String = "en-US"
    ): Call<MovieAdditionalDataItem>


    @GET("movie/{movie_id}/credits?api_key=$API_KEY")
    fun getMovieCastById(
        @Path("movie_id") movie_id: String,
        @Query("language") language: String = "en-US"
    ): Call<CastMovieResponse>


    @GET("person/{person_id}?api_key=$API_KEY")
    fun getCastPersonById(
        @Path("person_id") person_id: String,
        @Query("language") language: String = "en-US"
    ): Call<PersonCastResponse>

}