package ru.meseen.dev.androidacademy

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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
import ru.meseen.dev.androidacademy.workers.BaseRefreshWorker


@RunWith(JUnit4::class)
class WorkerTest {

    private lateinit var context: Context
    private lateinit var application: App
    private val service = RetrofitClient.movieService
    private val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @ExperimentalSerializationApi
    private lateinit var repository: Repository

    companion object {
        const val TAG = "WorkerTestFifa"
    }

    @ExperimentalSerializationApi
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        application = (context as App)
        repository = application.repository
    }

    @ExperimentalSerializationApi
    @Test
    fun testMyWorker() {
        val worker =
            TestListenableWorkerBuilder<BaseRefreshWorker>(context)
                .build()
        val cor = worker.startWork()
        val result = cor.get()
        val data = workDataOf("Strins" to "BaseRefreshWorker  doWork: ")
        assertThat(result, `is`(ListenableWorker.Result.success(data)))
    }

    @ExperimentalSerializationApi
    @Test
    fun dataEquals() {
        mainScope.launch(Dispatchers.IO) {

            val genresItems = service.getGenresList("ru-RU")
            val genres =
                genresItems.genres.map { it.genresId to it.genresName }.toMap()

            val topRated = service.loadList("top_rated", "ru-RU", 1, "RU").results.transform(
                "top_rated",
                genres
            )
            val nowPlaying = service.loadList("now_playing", "ru-RU", 1, "RU").results.transform(
                "now_playing",
                genres
            )
            val popular = service.loadList("popular", "ru-RU", 1, "RU").results.transform(
                "popular",
                genres
            )
            val upcoming = service.loadList("upcoming", "ru-RU", 1, "RU").results.transform(
                "upcoming",
                genres
            )

            val topRatedBD = repository.dataBase.movieDao().getTestsList("top_rated")
            val nowPlayingBD = repository.dataBase.movieDao().getTestsList("now_playing")
            val popularBD = repository.dataBase.movieDao().getTestsList("popular")
            val upcomingBD = repository.dataBase.movieDao().getTestsList("upcoming")

            Log.wtf(TAG, "testMyWork:topRated equals = ${topRated[0] == topRatedBD[0]}")
            Log.wtf(TAG, "testMyWork:nowPlaying equals = ${nowPlaying[0] == nowPlayingBD[0]}")
            Log.wtf(TAG, "testMyWork:popular equals = ${popular[0] == popularBD[0]}")
            Log.wtf(TAG, "testMyWork:upcoming equals = ${upcoming[0] == upcomingBD[0]}")

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
                movieItem.genreIds.map { genres[it] }.joinToString()
            )
        }

    }
}