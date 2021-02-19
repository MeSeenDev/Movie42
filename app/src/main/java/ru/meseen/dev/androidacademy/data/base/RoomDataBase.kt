package ru.meseen.dev.androidacademy.data.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import ru.meseen.dev.androidacademy.data.base.dao.MovieListableDao
import ru.meseen.dev.androidacademy.data.base.dao.PageKeysDao
import ru.meseen.dev.androidacademy.data.base.entity.*

@Database(
    entities = [MovieDataEntity::class, CastEntity::class, GenresEntity::class, PageKeyEntity::class, MovieAdditionalDataEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieListableDao
    abstract fun pageKeyDao(): PageKeysDao

    companion object {
        private const val DATA_BASE_NAME = "MOVIE_DATA_BASE.db"
        const val TABLE_NAME = "MOVIE_TABLE"
        const val CAST_TABLE_NAME = "CAST_TABLE"
        const val GENRES_TABLE_NAME = "GENRES_TABLE"
        const val PAGE_TABLE_NAME = "PAGE_TABLE_NAME"
        const val MOVIE_ADDITIONAL_TABLE_NAME = "MOVIE_ADDITIONAL_TABLE_NAME"


        @Volatile
        private var INSTANCE: RoomDataBase? = null

        fun getDatabase(applicationContext: Context): RoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    applicationContext.applicationContext,
                    RoomDataBase::class.java,
                    DATA_BASE_NAME
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }




}