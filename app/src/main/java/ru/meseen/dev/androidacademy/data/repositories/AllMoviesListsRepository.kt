package ru.meseen.dev.androidacademy.data.repositories

import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity

interface AllMoviesListsRepository {
    suspend fun getAllMovies(): List<MovieDataEntity>
}