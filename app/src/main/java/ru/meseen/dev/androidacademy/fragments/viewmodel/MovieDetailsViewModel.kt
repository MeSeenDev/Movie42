package ru.meseen.dev.androidacademy.fragments.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity

class MovieDetailsViewModel(application: Application, private val handle: SavedStateHandle) :
    ViewModel() {


    fun getMovieByID(movieID: Long): LiveData<MovieItemEntity> {
        TODO()
    }


}