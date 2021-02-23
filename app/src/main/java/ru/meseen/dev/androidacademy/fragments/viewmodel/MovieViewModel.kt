package ru.meseen.dev.androidacademy.fragments.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.data.base.query.impl.MovieListQuery
import ru.meseen.dev.androidacademy.data.repositories.MovieListRepository
import ru.meseen.dev.androidacademy.support.ListType

class MovieViewModel(
    repository: MovieListRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val KEY_MOVIES = "KEY_POSTS_ska"
        const val TYPE_QUERY = "top_rated"
        const val TAG = "MovieViewModel"
    }

    init{
        if (!handle.contains(KEY_MOVIES)) {
            handle.set(KEY_MOVIES, TYPE_QUERY)
        }
    }


    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @ExperimentalPagingApi
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val movies = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        handle.getLiveData<String>(KEY_MOVIES)
            .asFlow()
            .flatMapLatest {
                Log.d(TAG, "switchTypeList:flatMapLatest $it")

                repository.loadMoviesList(
                    query = MovieListQuery(
                        path = it,
                        page = 1,
                        region = "RU",
                        language = "ru-RU"
                    ), pageSize = 20
                )
            }.cachedIn(viewModelScope)
    ).flattenMerge(2)

    @ExperimentalSerializationApi
    private fun checkIfTypeDataPresent(listType: ListType) =
        (handle.get<String>(KEY_MOVIES)) != (listType.selection)

    @ExperimentalSerializationApi
    fun switchTypeList(listType: ListType) {
        if (!checkIfTypeDataPresent(listType)) return
        Log.d(TAG, "switchTypeList: ${listType.selection}")
        clearListCh.offer(Unit)
        handle.set(KEY_MOVIES, listType.selection)
    }
}

