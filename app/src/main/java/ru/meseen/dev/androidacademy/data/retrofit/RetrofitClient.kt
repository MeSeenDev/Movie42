package ru.meseen.dev.androidacademy.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.meseen.dev.androidacademy.data.retrofit.service.MovieService

object RetrofitClient {

    const val API_KEY = "2db156c9dbfce70974c451fe2efd50af"
    const val LANGUAGE = "ru"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    private const val API_KEY_HEADER = "x-api-key"

    private class CatsApiHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url

            val request = originalRequest.newBuilder()
                .url(originalHttpUrl)
                .addHeader(API_KEY_HEADER, API_KEY)
                .build()

            return chain.proceed(request)
        }
    }

    fun getImageUrl(imageUrl: String): String {
        return BASE_IMAGE_URL + imageUrl
    }

    private val okHttpClient =
        OkHttpClient().newBuilder().addInterceptor(CatsApiHeaderInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val movieService: MovieService = retrofit.create(MovieService::class.java)

}