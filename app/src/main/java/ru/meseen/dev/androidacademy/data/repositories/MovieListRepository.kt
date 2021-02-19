package ru.meseen.dev.androidacademy.data.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity
import ru.meseen.dev.androidacademy.data.base.query.MovieListableQuery

interface MovieListRepository {
    fun loadMoviesList(query: MovieListableQuery, pageSize: Int): Flow<PagingData<MovieDataEntity>>

}