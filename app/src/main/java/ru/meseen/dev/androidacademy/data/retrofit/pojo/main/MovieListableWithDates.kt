package ru.meseen.dev.androidacademy.data.retrofit.pojo.main

interface MovieListableWithDates : MovieListable {
    fun getMovieDates():MovieDates
}