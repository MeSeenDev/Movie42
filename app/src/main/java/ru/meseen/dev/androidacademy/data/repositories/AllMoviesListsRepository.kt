package ru.meseen.dev.androidacademy.data.repositories

import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity

interface AllMoviesListsRepository {
    fun getAllMovies(): List<MovieDataEntity>
}