package ru.meseen.dev.androidacademy.fragments.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.data.base.App
@ExperimentalPagingApi
@ExperimentalSerializationApi
class MovieViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val application: Application,
    args: Bundle? = null
) :
    AbstractSavedStateViewModelFactory(owner, args) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {

        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(
                repository = (application as App).repository,
                handle = handle
            ) as T
        }
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(
                repository = (application as App).repository,
                handle = handle
            ) as T
        }
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                repository = (application as App).repository,
                handle = handle
            ) as T
        }

        throw  IllegalArgumentException("Не занаю такой вью модели")
    }

}