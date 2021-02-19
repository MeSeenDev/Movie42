package ru.meseen.dev.androidacademy.data.base.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.meseen.dev.androidacademy.data.base.entity.PageKeyEntity

@Dao
interface PageKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<PageKeyEntity>)

    @Query("SELECT * FROM PAGE_TABLE_NAME WHERE movieId = :id AND listType = :listType")
    suspend fun remoteKeyById(id: Long, listType: String): PageKeyEntity?

    @Query("DELETE FROM PAGE_TABLE_NAME WHERE listType = :listType")
    suspend fun deleteByListType(listType: String): Int

    @Query("DELETE FROM PAGE_TABLE_NAME")
    suspend fun clearRemoteKeys()
}