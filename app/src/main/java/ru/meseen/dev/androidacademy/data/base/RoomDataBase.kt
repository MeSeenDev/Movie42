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
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.data.CastData
import ru.meseen.dev.androidacademy.data.dao.MovieDao
import ru.meseen.dev.androidacademy.data.entity.MovieEntity
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
                    .addCallback(MovieDatabaseCallback(context,scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }


    private class MovieDatabaseCallback(context: Context,
                                        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        private val fakeInternetData = listOf(

            MovieEntity(
                _id = 1,
                pg = 13,
                labelText = context.resources.getString(R.string.label_text),
                keywordsText = context.resources.getString(R.string.keywordsText),
                reviewsStars = 4,
                movieLength = 137,
                isFavorite = true,
                drawable = R.drawable.main_movie,
                reviewsText = context.resources.getString(R.string.reviewsText),
                descriptionText = context.resources.getString(R.string.descriptionText),
                cast = listOf(
                    CastData(name = "Robert Downey Jr.", drawable = R.drawable.movie),
                    CastData(name = "Chris Evans", drawable = R.drawable.movie_1),
                    CastData(name = "Mark Ruffalo", drawable = R.drawable.movie_2),
                    CastData(name = "Chris Hemsworth", drawable = R.drawable.movie_3),
                    CastData(name = "Robert Downey Jr.", drawable = R.drawable.movie),
                    CastData(name = "Chris Evans", drawable = R.drawable.movie_1),
                    CastData(name = "Mark Ruffalo", drawable = R.drawable.movie_2),
                    CastData(name = "Chris Hemsworth", drawable = R.drawable.movie_3)
                )
            ),
            MovieEntity(
                _id = 2,
                pg = 16,
                labelText = context.resources.getString(R.string.label_text0),
                keywordsText = context.resources.getString(R.string.keywordsText0),
                reviewsStars = 5,
                movieLength = 97,
                isFavorite = true,
                drawable = R.drawable.main_movie0,
                reviewsText = context.resources.getString(R.string.reviewsText0),
                descriptionText = context.resources.getString(R.string.descriptionText0),
                cast = listOf(
                    CastData(name = "Robert Downey Jr.", drawable = R.drawable.movie),
                    CastData(name = "Chris Evans", drawable = R.drawable.movie_1),
                    CastData(name = "Mark Ruffalo", drawable = R.drawable.movie_2),
                    CastData(name = "Chris Hemsworth", drawable = R.drawable.movie_3),
                    CastData(name = "Robert Downey Jr.", drawable = R.drawable.movie),
                    CastData(name = "Chris Evans", drawable = R.drawable.movie_1),
                    CastData(name = "Mark Ruffalo", drawable = R.drawable.movie_2),
                    CastData(name = "Chris Hemsworth", drawable = R.drawable.movie_3)
                )
            ),
            MovieEntity(
                _id = 3,
                pg = 13,
                labelText = context.resources.getString(R.string.label_text1),
                keywordsText = context.resources.getString(R.string.keywordsText1),
                reviewsStars = 4,
                movieLength = 102,
                drawable = R.drawable.main_movie1,
                reviewsText = context.resources.getString(R.string.reviewsText1),
                descriptionText = context.resources.getString(R.string.descriptionText1),
                cast = listOf(
                    CastData(name = "Robert Downey Jr.", drawable = R.drawable.movie),
                    CastData(name = "Chris Evans", drawable = R.drawable.movie_1),
                    CastData(name = "Mark Ruffalo", drawable = R.drawable.movie_2),
                    CastData(name = "Chris Hemsworth", drawable = R.drawable.movie_3)
                )
            ),
            MovieEntity(
                _id = 4,
                pg = 13,
                labelText = context.resources.getString(R.string.label_text2),
                keywordsText = context.resources.getString(R.string.keywordsText2),
                reviewsStars = 5,
                movieLength = 120,
                isFavorite = true,
                drawable = R.drawable.main_movie2,
                reviewsText = context.resources.getString(R.string.reviewsText2),
                descriptionText = context.resources.getString(R.string.descriptionText2),
                cast = listOf(
                    CastData(name = "Robert Downey Jr.", drawable = R.drawable.movie),
                    CastData(name = "Chris Evans", drawable = R.drawable.movie_1),
                    CastData(name = "Mark Ruffalo", drawable = R.drawable.movie_2),
                    CastData(name = "Chris Hemsworth", drawable = R.drawable.movie_3),
                    CastData(name = "Robert Downey Jr.", drawable = R.drawable.movie),
                    CastData(name = "Chris Evans", drawable = R.drawable.movie_1),
                    CastData(name = "Mark Ruffalo", drawable = R.drawable.movie_2),
                    CastData(name = "Chris Hemsworth", drawable = R.drawable.movie_3)
                )
            )
        )
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.movieDao())
                }
            }
        }

        suspend fun populateDatabase(movieDao: MovieDao) {
            movieDao.deleteAll()
            fakeInternetData.asSequence().forEach { movieDao.insert(it) }
        }
    }


}