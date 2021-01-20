package ru.meseen.dev.androidacademy.data.retrofit.pojo.main

import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.MovieItem

interface MovieListable {
    fun getMoviePage():Int
    fun getMovieTotalPages():Int
    fun getMovieResults():List<MovieItem>
    fun getMovieTotalResults():Int
}