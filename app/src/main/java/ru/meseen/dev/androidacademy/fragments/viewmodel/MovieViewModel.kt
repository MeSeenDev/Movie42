package ru.meseen.dev.androidacademy.fragments.viewmodel


import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.preference.PreferenceManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.data.base.query.impl.MovieListQuery
import ru.meseen.dev.androidacademy.data.repositories.MovieListRepository
import ru.meseen.dev.androidacademy.data.repositories.impl.Repository
import ru.meseen.dev.androidacademy.fragments.FragmentMoviesList
import ru.meseen.dev.androidacademy.support.ListType
import ru.meseen.dev.androidacademy.support.PreferencesKeys

@ExperimentalPagingApi
class MovieViewModel(
    private val repository: MovieListRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    private var sharedPrefs: SharedPreferences

    @ExperimentalSerializationApi
    @ExperimentalPagingApi

    companion object {
        const val KEY_MOVIES = "KEY_POSTS_ska"
        const val TYPE_QUERY = "top_rated"
        const val TAG = "MovieViewModel"
    }

    init{
        if (!handle.contains(KEY_MOVIES)) {
            handle.set(KEY_MOVIES, TYPE_QUERY)
        }
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences((repository as Repository).application).apply {

        }
    }



    @ExperimentalPagingApi
    private fun languageQuery() = sharedPrefs.getString(PreferencesKeys.LANGUAGE_KEY.key,PreferencesKeys.LANGUAGE_KEY.defaultKey) ?: PreferencesKeys.LANGUAGE_KEY.defaultKey

    @ExperimentalPagingApi
    private fun regionQuery() = sharedPrefs.getString(PreferencesKeys.REGION_KEY.key,PreferencesKeys.REGION_KEY.defaultKey) ?: PreferencesKeys.REGION_KEY.defaultKey



    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @ExperimentalStdlibApi
    @ExperimentalPagingApi
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val movies = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        handle.getLiveData<String>(KEY_MOVIES)
            .asFlow()
            .flatMapLatest {
                Log.d(TAG, "movies: flatMapLatest ${languageQuery()} ${regionQuery()}")
                repository.loadMoviesList(
                    query = MovieListQuery(
                        path = it,
                        page = 1,
                        region = regionQuery(),
                        language = languageQuery()
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
        forceSwitchTypeList(listType)
    }

    fun forceSwitchTypeList(listType: ListType){
        clearListCh.offer(Unit)
        handle.set(KEY_MOVIES, listType.selection)
    }


}

