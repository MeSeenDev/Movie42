package ru.meseen.dev.androidacademy.data.base.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.meseen.dev.androidacademy.data.base.entity.CastEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieAdditionalDataEntity

data class MovieItemEntity(
    @Embedded val movieAddData: MovieAdditionalDataEntity,
    @Relation(
        parentColumn = "id",
        entity = CastEntity::class,
        entityColumn = "film_id"
    )
    val castList: List<CastEntity>,

    )