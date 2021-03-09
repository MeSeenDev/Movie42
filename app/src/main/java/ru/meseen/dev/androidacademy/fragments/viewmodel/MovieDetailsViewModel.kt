package ru.meseen.dev.androidacademy.fragments.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieDetailViewItems
import ru.meseen.dev.androidacademy.data.base.query.MovieByIdQuery
import ru.meseen.dev.androidacademy.data.base.query.impl.MovieItemQuery
import ru.meseen.dev.androidacademy.data.repositories.MovieItemRepository
class MovieDetailsViewModel(
    private val repository: MovieItemRepository,
    private val handle: SavedStateHandle
) :
    ViewModel() {


    companion object {
        const val KEY_MOVIE_ITEM = "KEY_MOVIE_ITEM"
        const val TAG = "MovieDetailsViewModel"
    }


    private lateinit var job: Job

    private var _movieID: MutableStateFlow<MutableList<MovieDetailViewItems>> = MutableStateFlow(
        mutableListOf()
    )
    private val exceptionHandler = CoroutineExceptionHandler{ context, throwable ->
        Log.wtf(TAG, ":Error MovieDetailsViewModel  CoroutineExceptionHandler $throwable")
    }

    fun loadMovieItem(
        query: MovieByIdQuery
    ): StateFlow<List<MovieDetailViewItems>> {
        job = viewModelScope.launch(exceptionHandler + Dispatchers.Default) {
            if(_movieID.value.isEmpty()){
                repository.loadMovieItem(query).collectLatest { _movieID.upgrade(it) }
            }
        }


        return _movieID

    }




    @ExperimentalCoroutinesApi
    private fun <T: MovieDetailViewItems> MutableStateFlow<MutableList<MovieDetailViewItems>>.upgrade(item: T ){
        Log.d(TAG, "upgrade: ${_movieID.value.size}")
        val list = mutableListOf<MovieDetailViewItems>()

        list.contains(item)
        list.addAll(this.value)
        list.add(item)
        this.value =list


    }

    private fun getQueue() = handle.get<MovieItemQuery>(KEY_MOVIE_ITEM)

    fun updateQuery(query: MovieByIdQuery) {
        handle.set(KEY_MOVIE_ITEM, query)
    }

    fun clearList() {
        Log.d(TAG, "clear: ${_movieID.value}")
       _movieID.value = mutableListOf()
    }


}


