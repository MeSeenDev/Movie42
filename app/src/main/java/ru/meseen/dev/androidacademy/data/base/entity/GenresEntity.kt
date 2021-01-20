package ru.meseen.dev.androidacademy.data.base.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.GENRES_TABLE_NAME
import ru.meseen.dev.androidacademy.data.retrofit.pojo.items.GenresItem


@Entity(
    tableName = GENRES_TABLE_NAME,
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
data class GenresEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val genres_id: Int,
    @ColumnInfo(name = "name")
    val genresName: String,
    @ColumnInfo(name = "language")
    val language: String,
) {

    constructor(genresItems: GenresItem, language: String) : this(
        genres_id = genresItems.genresId,
        genresName = genresItems.genresName,
        language = language,

    )


}


