package ru.meseen.dev.androidacademy.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.androidacademy.data.entity.MovieEntity

@Dao
interface MovieDao {

    // Сортировки по алфавиту и пр напишу через sql в будущем
    @Query("SELECT * FROM movie_table ORDER BY isFavorite DESC")
    fun getMovieList(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}