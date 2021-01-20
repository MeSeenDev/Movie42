package ru.meseen.dev.androidacademy.data

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.meseen.dev.androidacademy.data.base.App
import ru.meseen.dev.androidacademy.data.base.dao.MovieDao
import ru.meseen.dev.androidacademy.data.base.entity.MovieEntity
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient
import ru.meseen.dev.androidacademy.data.retrofit.pojo.main.MovieListable

class Repository(application: Application, private val movieDao: MovieDao) {

    val moviesList: Flow<List<MovieEntity>> = movieDao.getMovieList()
    val scope = (application as App).applicationScope // на будующее

    private val service = RetrofitClient.movieService

    init {
        loadData(ListType.TOP_VIEW_LIST)
    }

    var moviesLists: MutableLiveData<MovieListable> = MutableLiveData()


    fun loadData(
        listType: ListType,
        language: String = "en-US",
        page: Int = 1,
        region: String = ""
    ): LiveData<MovieListable> {

        val call = when (listType) {
            ListType.TOP_VIEW_LIST -> service.getTopRatedMovies(language, page, region).enqueue(MyCallBack())
            ListType.NOW_PLAYING_VIEW__LIST -> service.getNowPlayingMovies(language, page, region).enqueue(MyCallBack())
            ListType.POPULAR_VIEW_LIST -> service.getPopularMovies(language, page, region).enqueue(MyCallBack())
            else -> service.getUpComing(language, page, region).enqueue(MyCallBack())
        }
        Log.d("TAG", "loadData: ${call.javaClass}")

        return moviesLists
    }

    /*fun getMovieById(movie_id: Int, language: String): LiveData<MovieListable> {
        TODO()
    }

    fun getMovieCastById(movie_id: Int, language: String): LiveData<MovieListable> {
        TODO()
    }

    fun getCastPersonById(person_id: Int, language: String): LiveData<MovieListable> {
        TODO()
    }*/

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


    private inner class MyCallBack<T : MovieListable?> : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                moviesLists.value = response.body()
                Log.v("TAG", "onResponse: Fine ${response.body()}")

            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            moviesLists.value = null
        }

    }

    enum class ListType {
        TOP_VIEW_LIST, NOW_PLAYING_VIEW__LIST, FAVORITE_VIEW, POPULAR_VIEW_LIST, UPCOMING_VIEW_LIST
    }

}