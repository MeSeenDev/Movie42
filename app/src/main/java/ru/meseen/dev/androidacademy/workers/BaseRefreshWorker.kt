package ru.meseen.dev.androidacademy.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.paging.*
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.data.MovieRemoteMediator
import ru.meseen.dev.androidacademy.data.base.App
import ru.meseen.dev.androidacademy.data.base.RoomDataBase
import ru.meseen.dev.androidacademy.data.base.query.impl.MovieListQuery
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient
import ru.meseen.dev.androidacademy.support.ListType
import ru.meseen.dev.androidacademy.support.ListType.*
import ru.meseen.dev.androidacademy.workers.notifications.Notifiable
import ru.meseen.dev.androidacademy.workers.notifications.NotifyNewMovie

class BaseRefreshWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    companion object {
        private const val TAG = "BaseRefreshWorker"
    }

    @ExperimentalPagingApi
    @ExperimentalSerializationApi
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {

            val repository = (applicationContext as App).repository
            val dataBase = repository.dataBase

            var moviesBefore = repository.getAllMovies()
            //for test
            //moviesBefore = repository.getAllMovies().subList(1, moviesBefore.size) // todo для тестов ставляю два

            ListType.values().forEach {
                if (it != SEARCH_VIEW_LIST) {
                    updateMainLists(dataBase = dataBase, path = it.selection)
                }
            }
            val moviesAfter = repository.getAllMovies()

            val diffList = moviesAfter.asSequence().filter { !(moviesBefore.contains(it)) }.toList()
            if (diffList.isNotEmpty()) {
                diffList.forEach { Log.d(TAG, "doWork: ${it.title}") }
                val item = diffList.last()
                movieNotify(item)

            }

            val data = workDataOf("Success" to "PASS")
            Result.success(data)
        } catch (wtf: Throwable) {
            Log.wtf(TAG, "doWork: ${wtf.localizedMessage}")
            Result.retry()
        }
    }


    private fun movieNotify(notifiable: Notifiable) {
        Glide.with(applicationContext)
            .asBitmap()
            .load(RetrofitClient.getImageUrl(notifiable.posterPath))
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    NotifyNewMovie(
                        context = applicationContext, notifiable, resource
                    ).show()

                }
                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }


    @ExperimentalSerializationApi
    @ExperimentalPagingApi
    private suspend fun updateMainLists(dataBase: RoomDataBase, path: String) {

        val listMediator = MovieRemoteMediator(
            query = MovieListQuery(
                path = path,
                page = 1,
                region = "RU",
                language = "ru-RU"
            ), RetrofitClient.movieService, dataBase
        )
        listMediator.load(
            LoadType.REFRESH,
            PagingState(
                pages = listOf(
                    PagingSource.LoadResult.Page(
                        listOf(), // т.к небыло других
                        null, null // т.к небыло других
                    )
                ), config = PagingConfig(pageSize = 20) // Апи дает 20
                , anchorPosition = null // 0 т.к загрузка самая первая
                , leadingPlaceholderCount = 0
            )
        )
    }
}