package ru.meseen.dev.androidacademy.data.base.dao

import androidx.paging.PagingSource
import androidx.room.*
import ru.meseen.dev.androidacademy.data.base.entity.CastEntity
import ru.meseen.dev.androidacademy.data.base.entity.GenresEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieAdditionalDataEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieEntity
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity

@Dao
interface MovieListableDao {

    // Сортировки по алфавиту и пр напишу через sql в будущем
    @Query("SELECT * FROM MOVIE_TABLE  WHERE listType LIKE :listType")
    fun getMovieList(listType: String): PagingSource<Int, MovieDataEntity>

    @Query("SELECT * FROM GENRES_TABLE WHERE id LIKE :genreId")
    fun getGenreEntity(genreId: String): GenresEntity

    @Query("SELECT * FROM GENRES_TABLE WHERE language LIKE :language")
    fun getListGenreEntity(language: String): List<GenresEntity>

    @Query("DELETE FROM MOVIE_TABLE WHERE listType = :listType")
    suspend fun deleteByListType(listType: String)


    @Transaction
    @Query("SELECT * FROM MOVIE_TABLE WHERE listType LIKE :listType")
    fun getListMovies(listType: String): PagingSource<Int, MovieEntity>


    suspend fun insert(movieItemEntity: MovieItemEntity) {
        val movieAdditionalDataEntity = movieItemEntity.movieAddData
        insert(movieAdditionalDataEntity)
        val castEntity = movieItemEntity.castList
        castEntity.forEach{it.film_id = movieAdditionalDataEntity.id.toLong() }
        insertAll(*castEntity.toTypedArray())
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieAdditionalDataEntity: MovieAdditionalDataEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDataEntity: MovieDataEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(castEntity: CastEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genresEntity: GenresEntity): Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg movieDataEntity: MovieDataEntity): Array<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg castEntity: CastEntity): Array<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg genresEntity: GenresEntity): Array<Long>


    @Update
    suspend fun update(movieDataEntity: MovieDataEntity)

    @Update
    suspend fun updateAll(vararg movieDataEntity: MovieDataEntity)

    @Delete
    suspend fun delete(movieDataEntity: MovieDataEntity)

    @Delete
    suspend fun deleteAll(vararg movieDataEntity: MovieDataEntity)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()


}