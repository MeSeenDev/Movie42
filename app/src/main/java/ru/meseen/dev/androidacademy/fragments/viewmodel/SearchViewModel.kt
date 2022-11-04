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
import ru.meseen.dev.androidacademy.data.base.query.MovieSearchQuery
import ru.meseen.dev.androidacademy.data.base.query.impl.SearchViewQuery
import ru.meseen.dev.androidacademy.data.repositories.SearchRepository
import ru.meseen.dev.androidacademy.support.ListType.SEARCH_VIEW_LIST

class SearchViewModel(
    val repository: SearchRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val KEY_SEARCH_MOVIES = "KEY_SEARCH_MOVIES"
        val default = SearchViewQuery(query = "Star Wars",path = SEARCH_VIEW_LIST.selection)
        const val TAG = "SearchFragment.VM"
    }

    init {
        if (!handle.contains(KEY_SEARCH_MOVIES)) {
            handle.set(KEY_SEARCH_MOVIES, default)
            Log.d(TAG, ": query contains")

        }
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @ExperimentalPagingApi
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchMovies = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        handle.getLiveData<MovieSearchQuery>(KEY_SEARCH_MOVIES)
            .asFlow()
            .flatMapLatest { query ->
                Log.d(TAG, ": query $query")
                repository.search(
                    query = SearchViewQuery(
                        path = query.getMoviePath(),
                        page = query.getMoviesPage(),
                        region = query.getMoviesRegion(),
                        language = query.getMoviesLanguage(),
                        query = query.getSearchQuery(),
                        year = query.getMovieYear(),
                        primary_release_year = query.getMoviePrimaryReleaseYear(),
                        include_adult = query.isMovieIncludeAdult()
                    ), pageSize = 20
                )
            }.cachedIn(viewModelScope)
    ).flattenMerge(2)

    @ExperimentalSerializationApi
    private fun checkIfTypeDataPresent(query: SearchViewQuery) =
        (handle.get<SearchViewQuery>(KEY_SEARCH_MOVIES)) == query


    @ExperimentalSerializationApi
    fun reQuery(query: SearchViewQuery) {
        if (checkIfTypeDataPresent(query)) return
        clearListCh.trySend(Unit)
        handle.set(KEY_SEARCH_MOVIES, query)
    }

    fun clearSearchResults(){
            handle.get<MovieSearchQuery>(KEY_SEARCH_MOVIES)?.getMoviePath()?.let {
                repository.clearSearchBDQuery(it)
            }
            handle.remove<MovieSearchQuery>(KEY_SEARCH_MOVIES)
    }

    override fun onCleared() {
        clearSearchResults()
        super.onCleared()
    }
}