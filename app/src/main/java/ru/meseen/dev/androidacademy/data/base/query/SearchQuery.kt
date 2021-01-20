package ru.meseen.dev.androidacademy.data.base.query

interface SearchQuery : MovieListableQuery {
    fun getSearchQuery(): String
}


