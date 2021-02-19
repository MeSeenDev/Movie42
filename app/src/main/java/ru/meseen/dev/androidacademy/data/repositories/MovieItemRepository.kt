package ru.meseen.dev.androidacademy.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.meseen.dev.androidacademy.data.base.entity.MovieAdditionalDataEntity
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity
import ru.meseen.dev.androidacademy.data.base.query.CreditsQuery
import ru.meseen.dev.androidacademy.data.base.query.MovieByIdQuery

interface MovieItemRepository {
    fun loadMovieItem(query: MovieByIdQuery): LiveData<MovieItemEntity>
    fun clearMovieItem()
}