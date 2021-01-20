package ru.meseen.dev.androidacademy.data.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity
import ru.meseen.dev.androidacademy.data.base.query.CreditsQuery

interface MovieItemRepository {
    fun loadMovieItem(query: CreditsQuery, pageSize: Int): Flow<PagingData<MovieItemEntity>>
}