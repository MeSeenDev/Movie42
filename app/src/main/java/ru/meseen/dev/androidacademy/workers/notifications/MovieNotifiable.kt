package ru.meseen.dev.androidacademy.workers.notifications

interface MovieNotifiable {
    fun show(): MovieNotifiable
    fun dismiss(): MovieNotifiable
}