package ru.meseen.dev.androidacademy.fragments.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.meseen.dev.androidacademy.data.Repository
import ru.meseen.dev.androidacademy.data.base.entity.MovieEntity
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItem

class MovieViewModel(private val repository: Repository) : ViewModel() {

    val allWords: LiveData<List<MovieEntity>> = repository.moviesList.asLiveData()

    //Вместо изменяемой LifeData удачней получилось использовать map из одной в другую
    private val currentList: LiveData<List<MovieItem>> = Transformations.map(repository.moviesLists){
        listable -> listable.let {listable?.getMovieResults()}
    }

    fun getMoviesList(listType: Repository.ListType): LiveData<List<MovieItem>> {
        updateListData(listType)
        return currentList
    }

    fun updateListData(listType: Repository.ListType) {
        repository.loadData(listType)
    }


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