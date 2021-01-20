package ru.meseen.dev.androidacademy.data.base.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.PAGE_TABLE_NAME

@Entity(tableName = PAGE_TABLE_NAME)
data class PageKeyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    val _id: Long? = null,
    @ColumnInfo(name = "prevPage")
    val prevPage: Int?,
    @ColumnInfo(name = "nextPage")
    val nextPage: Int?,
    @ColumnInfo(name = "listType")
    val listType: String)