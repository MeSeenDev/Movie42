package ru.meseen.dev.androidacademy.data.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity
import ru.meseen.dev.androidacademy.data.base.query.MovieSearchQuery

interface SearchRepository {
    fun search(query: MovieSearchQuery, pageSize: Int): Flow<PagingData<MovieDataEntity>>
    suspend fun clearSearchBDQuery(listType: String)
}