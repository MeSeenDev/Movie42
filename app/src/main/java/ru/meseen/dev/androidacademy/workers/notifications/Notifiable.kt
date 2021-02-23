package ru.meseen.dev.androidacademy.workers.notifications

abstract class Notifiable(
    open val movieId: Long,
    open val title: String,
    open val description: String,
    open val posterPath: String?
)