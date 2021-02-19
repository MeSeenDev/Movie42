package ru.meseen.dev.androidacademy.fragments.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity
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
    }

    private var movieID: LiveData<MovieItemEntity> = MutableLiveData()

    fun loadMovieItem(
        query: MovieByIdQuery
    ): LiveData<MovieItemEntity> {
        updateQuery(query)
        viewModelScope.launch {
            Log.d(KEY_MOVIE_ITEM, "loadMovieItem: $movieID ")
            getQueue()?.let {
                movieID = repository.loadMovieItem(it)
            }
        }
        return movieID
    }

    private fun getQueue() = handle.get<MovieItemQuery>(KEY_MOVIE_ITEM)

    fun updateQuery(query: MovieByIdQuery) {
        handle.set(KEY_MOVIE_ITEM, query)
    }

    fun clear() = repository.clearMovieItem()



}