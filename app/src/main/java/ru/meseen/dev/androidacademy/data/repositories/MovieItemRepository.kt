package ru.meseen.dev.androidacademy.data.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieDetailViewItems
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity
import ru.meseen.dev.androidacademy.data.base.query.MovieByIdQuery

interface MovieItemRepository {
    fun loadMovieItem(query: MovieByIdQuery): Flow<MovieDetailViewItems>

}