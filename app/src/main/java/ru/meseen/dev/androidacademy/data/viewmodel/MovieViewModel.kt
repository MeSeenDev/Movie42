package ru.meseen.dev.androidacademy.data.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.meseen.dev.androidacademy.data.Repository
import ru.meseen.dev.androidacademy.data.base.entity.MovieEntity

class MovieViewModel(private val repository: Repository) : ViewModel() {
    val allWords: LiveData<List<MovieEntity>> = repository.moviesList.asLiveData()


    fun insert(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.insert(movieEntity)
    }
}

class MovieViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}