package ru.meseen.dev.androidacademy.data.base.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import ru.meseen.dev.androidacademy.data.base.entity.CastEntity
import ru.meseen.dev.androidacademy.data.base.entity.GenresEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieAdditionalDataEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity
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
    suspend fun deleteByListType(listType: String): Int


    @Query("DELETE FROM MOVIE_ADDITIONAL_TABLE_NAME WHERE id = :movie_iD")
    suspend fun deleteByID(movie_iD: Int): Int


    /*fun getMovieByIDs(movie_iD: Int, language: String = "en-US"): Flow<MovieItemEntity> {
        val movieAdditionalDataEntity = getMovieByID(movie_iD)
        return movieAdditionalDataEntity.map {
            val genreIds = it?.genresIDs?.split(",").toTypedArray()
            val castIds = it?.castIDs?.split(",").toTypedArray()
            val genresEntity = getGenresListByIDs(language = language, genresIDs = genreIds)
            val castEntity = getCastListByIDs(language = language, castIDs = castIds)
            MovieItemEntity(it, genresEntity.value, castEntity.value)
        }.asFlow()
    }*/


    @Query("SELECT * FROM MOVIE_ADDITIONAL_TABLE_NAME  WHERE id LIKE :movie_iD")
    fun getMovieByID(movie_iD: Int): LiveData<MovieAdditionalDataEntity>

    @Query("SELECT * FROM CAST_TABLE WHERE id IN (:castIDs) AND language LIKE :language")
    fun getCastListByIDs(vararg castIDs: String, language: String): LiveData<List<CastEntity>>

    @Query("SELECT * FROM GENRES_TABLE WHERE id IN (:genresIDs) AND language LIKE :language")
    fun getGenresListByIDs(vararg genresIDs: String, language: String): LiveData<List<GenresEntity>>


    suspend fun insert(movieItemEntity: MovieItemEntity) {
        val movieAdditionalDataEntity = movieItemEntity.movieAddData
        val castEntity = movieItemEntity.castList
        val genresEntity = movieItemEntity.genresEntity
        insert(movieAdditionalDataEntity)
        insertAll(*castEntity.toTypedArray())
        insertAll(*genresEntity.toTypedArray())
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieAdditionalDataEntity: MovieAdditionalDataEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDataEntity: MovieDataEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(castEntity: CastEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genresEntity: GenresEntity): Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg movieDataEntity: MovieDataEntity): Array<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movieDataEntity: List<MovieDataEntity>): Array<Long>

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


    /// TESTS

    @Query("SELECT * FROM MOVIE_TABLE  WHERE listType LIKE :listType")
    fun getTestsList(listType: String): List<MovieDataEntity>

    @Query("SELECT * FROM MOVIE_TABLE")
    fun getAllMoviesListsRepository(): List<MovieDataEntity>


    @Query("DELETE FROM MOVIE_TABLE")
    suspend fun clearMovieLists()

    @Query("DELETE FROM MOVIE_TABLE WHERE listType LIKE :listType")
    suspend fun clearSearchBDQuery(listType: String)
}