package ru.meseen.dev.androidacademy.data.base.entity.relations

import ru.meseen.dev.androidacademy.data.base.entity.CastEntity
import ru.meseen.dev.androidacademy.data.base.entity.GenresEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieAdditionalDataEntity

data class MovieItemEntity(
  val movieAddData: MovieAdditionalDataEntity,
  val genresEntity: List<GenresEntity>,
  val castList: List<CastEntity>

)