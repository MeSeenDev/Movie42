package ru.meseen.dev.androidacademy.data.base.query

import java.io.Serializable

interface MovieByIdQuery : BaseQuery, Serializable {
    fun getMovieID(): Int
    fun getAppendToResponse(): String

}