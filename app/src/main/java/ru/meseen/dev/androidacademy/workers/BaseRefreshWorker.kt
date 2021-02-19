package ru.meseen.dev.androidacademy.workers

import android.content.Context
import androidx.paging.*
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.data.MovieRemoteMediator
import ru.meseen.dev.androidacademy.data.base.App
import ru.meseen.dev.androidacademy.data.base.RoomDataBase
import ru.meseen.dev.androidacademy.data.base.query.impl.MovieListQuery
import ru.meseen.dev.androidacademy.data.repositories.impl.Repository
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient

class BaseRefreshWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    @ExperimentalPagingApi
    @ExperimentalSerializationApi
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val repository = (applicationContext as App).repository
            val dataBase = repository.dataBase

            Repository.ListType.values().forEach {
                if (it != Repository.ListType.SEARCH_VIEW_LIST) {
                    updateMainLists(dataBase = dataBase, path = it.selection)
                }
            }

            val data = workDataOf("Strins" to "BaseRefreshWorker  doWork: ")
            Result.success(data)
        } catch (thr: Throwable) {
            Result.retry()
        }
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