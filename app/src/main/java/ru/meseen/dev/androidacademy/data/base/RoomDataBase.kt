package ru.meseen.dev.androidacademy.data.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.meseen.dev.androidacademy.data.base.dao.MovieDao
import ru.meseen.dev.androidacademy.data.base.entity.MovieEntity
import ru.meseen.dev.androidacademy.data.loadMovies
import ru.meseen.dev.androidacademy.data.typeconverters.CastTypeConverter

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(CastTypeConverter::class)
abstract class RoomDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val TABLE_NAME = "movie_table"

        @Volatile
        private var INSTANCE: RoomDataBase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBase::class.java,
                    TABLE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(MovieDatabaseCallback(context, scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }


    private class MovieDatabaseCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {


        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.movieDao(), loadMovies(context))
                }
            }
        }

        suspend fun populateDatabase(movieDao: MovieDao, fakeInternetData: List<MovieEntity>) {
            movieDao.deleteAll()
            fakeInternetData.asSequence().forEach { movieDao.insert(it) }


        }
    }


}