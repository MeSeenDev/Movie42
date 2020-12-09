package ru.meseen.dev.androidacademy.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.androidacademy.data.entity.MovieEntity

@Dao
interface MovieDao {

    // Сортировки по алфавиту и пр напишу через sql в будущем
    @Query("SELECT * FROM movie_table ORDER BY isFavorite DESC")
    fun getMovieList(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg movieEntity: MovieEntity)

    @Update
    suspend fun update(movieEntity: MovieEntity)

    @Update
    suspend fun updateAll(vararg movieEntity: MovieEntity)

    @Delete
    suspend fun delete(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteAll(vararg movieEntity: MovieEntity)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}