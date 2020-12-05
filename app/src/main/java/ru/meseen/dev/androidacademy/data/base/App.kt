package ru.meseen.dev.androidacademy.data.base

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.meseen.dev.androidacademy.data.Repository

class App : Application() {
    // Чтобы не получилось случайно затребовать 2 синглтона и получить падение производительности с синхронизацией а т.к использую by lazy должно создоваться по необходимости и один раз
    val applicationScope = CoroutineScope(SupervisorJob())
    private val dataBase by lazy { RoomDataBase.getDatabase(this, applicationScope) }
    val repository by lazy { Repository(this,dataBase.movieDao()) }


}