package ru.meseen.dev.androidacademy

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import ru.meseen.dev.androidacademy.data.base.App
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity
import ru.meseen.dev.androidacademy.data.repositories.impl.Repository
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItemResponse
import ru.meseen.dev.androidacademy.support.ListType.*
import ru.meseen.dev.androidacademy.workers.BaseRefreshWorker


@RunWith(JUnit4::class)
class WorkerTest {

    private lateinit var context: Context
    private lateinit var application: App
    @ExperimentalSerializationApi
    private val service = RetrofitClient.movieService

    @ExperimentalPagingApi
    @ExperimentalSerializationApi
    private lateinit var repository: Repository

    companion object {
        private const val TAG = "WorkerTestFifa"
        private const val LANGUAGE = "ru-RU"
        private const val REGION = "RU"
        private const val PAGE = 1
    }


    @ExperimentalPagingApi
    @ExperimentalSerializationApi
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        application = (context as App)
        repository = application.repository
    }

    @ExperimentalPagingApi
    @ExperimentalSerializationApi
    @Test
    fun testMyWorker() {
        val worker =
            TestListenableWorkerBuilder<BaseRefreshWorker>(context)
                .build()
        val cor = worker.startWork()
        val result = cor.get()
        val data = workDataOf("Success" to "PASS")
        assertThat(result, `is`(ListenableWorker.Result.success(data)))
    }

    @ExperimentalPagingApi
    @ExperimentalSerializationApi
    @Test
    fun dataEquals() {
        runBlocking {
            val genresItems = service.getGenresList(LANGUAGE)
            val genres =
                genresItems.genres.map { it.genresId to it.genresName }.toMap()

            val topRated = service.loadList(
                TOP_VIEW_LIST.selection,
                LANGUAGE,
                PAGE,
                REGION
            ).results.transform(
                TOP_VIEW_LIST.selection,
                genres
            )
            val nowPlaying = service.loadList(
                NOW_PLAYING_VIEW_LIST.selection,
                LANGUAGE,
                PAGE,
                REGION
            ).results.transform(
                NOW_PLAYING_VIEW_LIST.selection,
                genres
            )
            val popular =
                service.loadList(
                    POPULAR_VIEW_LIST.selection,
                    LANGUAGE,
                    PAGE,
                    REGION
                ).results.transform(
                    POPULAR_VIEW_LIST.selection,
                    genres
                )
            val upcoming =
                service.loadList(
                    UPCOMING_VIEW_LIST.selection,
                    LANGUAGE,
                    PAGE,
                    REGION
                ).results.transform(
                    UPCOMING_VIEW_LIST.selection,
                    genres
                )

            val topRatedBD = repository.dataBase.movieDao().getTestsList("top_rated")
            val nowPlayingBD = repository.dataBase.movieDao().getTestsList("now_playing")
            val popularBD = repository.dataBase.movieDao().getTestsList("popular")
            val upcomingBD = repository.dataBase.movieDao().getTestsList("upcoming")


            assertEquals(topRated, topRatedBD)
            Log.d(TAG, "testMyWork: top_rated")
            assertEquals(nowPlaying, nowPlayingBD)
            Log.d(TAG, "testMyWork: now_playing")
            assertEquals(popular, popularBD)
            Log.d(TAG, "testMyWork: popular")
            assertEquals(upcoming, upcomingBD)
            Log.d(TAG, "testMyWork: upcoming")

        }
    }


    private fun List<MovieItemResponse>.transform(
        path: String,
        genres: Map<Int, String>
    ): List<MovieDataEntity> {
        return map { movieItem ->
            MovieDataEntity(
                movieItem,
                path,
                movieItem.genreIds.map { genres[it]?.capitalize() }.joinToString()
            )
        }

    }
}