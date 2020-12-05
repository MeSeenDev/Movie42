package ru.meseen.dev.androidacademy.data

import android.app.Application
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.androidacademy.data.base.App
import ru.meseen.dev.androidacademy.data.dao.MovieDao
import ru.meseen.dev.androidacademy.data.entity.MovieEntity

class Repository(application: Application, private val movieDao: MovieDao) {

    val moviesList: Flow<List<MovieEntity>> = movieDao.getMovieList()
    val scope = (application as App).applicationScope // на будующее


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movieEntity: MovieEntity) {
        movieDao.insert(movieEntity)
    }


}