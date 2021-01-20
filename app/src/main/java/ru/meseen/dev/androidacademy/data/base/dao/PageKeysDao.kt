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

    @Query("SELECT * FROM PAGE_TABLE_NAME WHERE _id = :id")
    suspend fun remoteKeyById(id: Long): PageKeyEntity?

    @Query("DELETE FROM PAGE_TABLE_NAME WHERE listType = :listType")
    suspend fun deleteByListType(listType: String)
}