package ru.meseen.dev.androidacademy.workers.notifications

interface NotiFyBle {
    fun show(): NotiFyBle
    fun dismiss(): NotiFyBle
}