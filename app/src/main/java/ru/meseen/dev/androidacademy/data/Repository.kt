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


    @WorkerThread
    suspend fun update(movieEntity: MovieEntity) = movieDao.update(movieEntity)

    @WorkerThread
    suspend fun updateAll(vararg movieEntity: MovieEntity) = movieDao.updateAll(*movieEntity)


    @WorkerThread
    suspend fun insert(movieEntity: MovieEntity) = movieDao.insert(movieEntity)

    @WorkerThread
    suspend fun insertAll(vararg movieEntity: MovieEntity) = movieDao.insertAll(*movieEntity)

    @WorkerThread
    suspend fun delete(vararg movieEntity: MovieEntity) = movieDao.deleteAll(*movieEntity)

    /**
     * remove All data in RoomDataBase TABLE_NAME = "movie_table"
     */
    @WorkerThread
    suspend fun deleteAll() = movieDao.deleteAll()

}