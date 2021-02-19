package ru.meseen.dev.androidacademy.data.retrofit.pojo.main

import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItemResponse

interface MovieListable {
    fun getMoviePage():Int
    fun getMovieTotalPages():Int
    fun getMovieResults():List<MovieItemResponse>
    fun getMovieTotalResults():Int
}