package ru.meseen.dev.androidacademy.data.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.meseen.dev.androidacademy.data.retrofit.service.MovieService
import java.util.concurrent.TimeUnit

object RetrofitClient {

    const val API_KEY = "2db156c9dbfce70974c451fe2efd50af"
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w342"
    private const val API_KEY_HEADER = "api_key"
    const val START_PAGE = 1


    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val contentType = "application/json".toMediaType()

    @ExperimentalSerializationApi
    private val converter = json.asConverterFactory(contentType)

    private val okHttpClient =
        OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(CatsApiHeaderInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


    @ExperimentalSerializationApi
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(converter)
        .build()


    @ExperimentalSerializationApi
    val movieService: MovieService = retrofit.create(MovieService::class.java)

    fun getImageUrl(imageUrl: String?): String {
        return BASE_IMAGE_URL + imageUrl
    }

    private class CatsApiHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url

            val url = originalHttpUrl
                .newBuilder()
                .addQueryParameter(
                    name = API_KEY_HEADER,
                    value = API_KEY
                ) ///нужно для всех запросов
                .build()


            val request = originalRequest.newBuilder()
                .url(url = url)
                .build()

            return chain.proceed(request)
        }
    }


}