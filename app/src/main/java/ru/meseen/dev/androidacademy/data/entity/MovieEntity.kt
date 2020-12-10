package ru.meseen.dev.androidacademy.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.data.CastData
import ru.meseen.dev.androidacademy.data.base.RoomDataBase.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
@Parcelize
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0,
    val pg: Int = 0,
    val labelText: String  = "No data",
    val keywordsText: String = "Default keyword",
    val drawable: Int = R.drawable.main_movie2,
    val reviewsStars: Int = 0,
    val reviewsText: String = "default review",
    val descriptionText: String = " no description",
    val movieLength: Int = 60,
    var isFavorite: Boolean = false,
    val cast: List<CastData> = listOf(CastData())
) : Parcelable{

    //то что ходит как собака, ведет себя как собака и лает как собака является собакой
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieEntity

        if (labelText != other.labelText) return false
        if (descriptionText != other.descriptionText) return false

        return true
    }

    override fun hashCode(): Int {
        var result = labelText.hashCode()
        result = 31 * result + descriptionText.hashCode()
        return result
    }
}