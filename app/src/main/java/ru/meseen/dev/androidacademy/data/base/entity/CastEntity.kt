package ru.meseen.dev.androidacademy.data.base.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.CAST_TABLE_NAME

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
 * @param profilePath
 * @param adult Взрослый?
 * @param order Хер пойми что и зачем **
 * @param film_id id Связанного фильма
 */
@Entity(tableName = CAST_TABLE_NAME)
data class CastEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val personId: Int,
    val castId: Int,
    val character: String,
    val gender: Int? = null,
    val creditId: String,
    val knownForDepartment: String,
    val originalName: String,
    val popularity: Double,
    val name: String,
    val profilePath: String? = null,
    val adult: Boolean,
    val order: Int,
    @ColumnInfo(name = "film_id")
    var film_id: Long
)

