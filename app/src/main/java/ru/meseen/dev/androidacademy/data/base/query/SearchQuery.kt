package ru.meseen.dev.androidacademy.data.base.query

import java.io.Serializable

interface SearchQuery : MovieListableQuery, Serializable {
    fun getSearchQuery(): String
}


