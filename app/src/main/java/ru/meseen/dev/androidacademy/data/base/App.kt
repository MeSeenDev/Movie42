package ru.meseen.dev.androidacademy.data.base

import android.app.Application
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.data.repositories.impl.Repository
import ru.meseen.dev.androidacademy.workers.BaseRefreshWorker
import java.util.concurrent.TimeUnit

class App : Application() {

    companion object {
        const val UNIQUE_WORK_NAME = "Update movies Lists"
    }
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.wtf("ERROR", " $coroutineContext : ${throwable.localizedMessage}")
        }
    val applicationScope = CoroutineScope(SupervisorJob() + coroutineExceptionHandler)
    private val dataBase by lazy { RoomDataBase.getDatabase(this) }
    @ExperimentalSerializationApi
    val repository by lazy { Repository(this, dataBase) }


    override fun onCreate() {
        super.onCreate()

        val constraintsWorker = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val worker = PeriodicWorkRequestBuilder<BaseRefreshWorker>(8, TimeUnit.HOURS)
            .setConstraints(constraintsWorker)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(UNIQUE_WORK_NAME, ExistingPeriodicWorkPolicy.REPLACE, worker)

    }



}