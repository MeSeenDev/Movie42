package ru.meseen.dev.androidacademy.data.base.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.meseen.dev.androidacademy.data.base.entity.GenresEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity


data class MovieEntity(
    @Embedded val movieEntity: MovieDataEntity,
    @Relation(
        parentColumn = "id",
        entity = GenresEntity::class,
        entityColumn = "id"
    )
    val listGenresEntity: List<GenresEntity>
)