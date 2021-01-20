package ru.meseen.dev.androidacademy.data.base

import android.app.Application
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.data.repositories.impl.Repository

class App : Application() {
    // Чтобы не получилось случайно затребовать 2 синглтона и получить падение производительности с синхронизацией а т.к использую by lazy должно создоваться по необходимости и один раз

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.wtf("ERROR", " $coroutineContext : ${throwable.localizedMessage}") }
    val applicationScope = CoroutineScope(SupervisorJob() + coroutineExceptionHandler)

    private val dataBase by lazy { RoomDataBase.getDatabase(this, applicationScope) }

    @ExperimentalSerializationApi
    val repository by lazy { Repository(this, dataBase) }

}