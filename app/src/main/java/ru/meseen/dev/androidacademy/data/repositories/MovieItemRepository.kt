package ru.meseen.dev.androidacademy.data.repositories

import androidx.lifecycle.LiveData
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity
import ru.meseen.dev.androidacademy.data.base.query.MovieByIdQuery

interface MovieItemRepository {
    fun loadMovieItem(query: MovieByIdQuery): LiveData<MovieItemEntity>
    fun clearMovieItem()
}