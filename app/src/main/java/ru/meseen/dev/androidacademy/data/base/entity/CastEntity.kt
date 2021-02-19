package ru.meseen.dev.androidacademy.data.base.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.CAST_TABLE_NAME
import ru.meseen.dev.androidacademy.data.retrofit.pojo.CastItem

/**
 *
 * @param personId Id для получения данных о персоне
 * @param castId Id актера
 * @param character Имя персонажа
 * @param gender Пол актера
 * @param creditId Это говно дает key к некоторым фильмам
 * @param knownForDepartment Кем он был на сьемках
 * @param originalName Настоящее имя Актера
 * @param popularity Рейтинг популярности
 * @param name Имя/Псевдоним актера
 * @param profileImage Ссылка на фото
 * @param adult Взрослый?
 * @param order Хер пойми что и зачем **
 * @param language Язык данных
 */
@Entity(tableName = CAST_TABLE_NAME)
data class CastEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val personId: Int,

    @ColumnInfo(name = "castId")
    val castId: Int,

    @ColumnInfo(name = "character")
    val character: String,

    @ColumnInfo(name = "gender")
    val gender: Int? = null,

    @ColumnInfo(name = "creditId")
    val creditId: String,

    @ColumnInfo(name = "knownForDepartment")
    val knownForDepartment: String,

    @ColumnInfo(name = "originalName")
    val originalName: String,

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "profilePath")
    val profileImage: String? = null,

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "order")
    val order: Int,

    @ColumnInfo(name = "language")
    val language: String,
) {
    constructor(castItem: CastItem, language: String) : this(
        personId = castItem.id,
        castId = castItem.castId,
        character = castItem.character,
        gender = castItem.gender,
        creditId = castItem.creditId,
        knownForDepartment = castItem.knownForDepartment,
        originalName = castItem.originalName,
        popularity = castItem.popularity,
        name = castItem.name,
        profileImage = castItem.profilePath,
        adult = castItem.adult,
        order = castItem.order,
        language = language
    )
}

