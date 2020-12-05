package ru.meseen.dev.androidacademy.adapter

import android.app.Application
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.data.entity.MovieEntity

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var goupView: ConstraintLayout = itemView.findViewById(R.id.mainItemGroup)
    var imageView: ImageView = itemView.findViewById(R.id.imageMainItemView)
    var keywordsText: TextView = itemView.findViewById(R.id.keywordsText)
    var favImageView: ImageView = itemView.findViewById(R.id.favImageView)
    var reviewsText: TextView = itemView.findViewById(R.id.reviewsText)
    var labelMainText: TextView = itemView.findViewById(R.id.labelMainText)
    var movieLengthText: TextView = itemView.findViewById(R.id.movieLengthText)
    var pgMainText: TextView = itemView.findViewById(R.id.pgMainText)
    var ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

    fun bind(itemMovieData: MovieEntity, application: Application) {
        var temp = "${itemMovieData.pg}+"
        pgMainText.text = temp
        labelMainText.text = itemMovieData.labelText
        ratingBar.rating = itemMovieData.reviewsStars.toFloat()
        reviewsText.text = itemMovieData.reviewsText
        keywordsText.text = itemMovieData.keywordsText
        temp = "${itemMovieData.movieLength}MIN"
        movieLengthText.text = temp

        imageView.background =
            ContextCompat.getDrawable(application.applicationContext, itemMovieData.drawable)

        val start = ContextCompat.getColor(application, R.color.start_of_shape)
        val center = ContextCompat.getColor(application, R.color.center_of_shape)
        val end = ContextCompat.getColor(application, R.color.end_of_shape)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            intArrayOf(start, center, end)
        )
        gradientDrawable.setGradientCenter(0.6f, 0.5f)
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        /*  Вариант с тенью из xml как тест разницы и то и то говно. И никто не скажет что делать. Вырубить тени 27-- не вариант
        imageView.background =
            ContextCompat.getDrawable(application.applicationContext, itemMovieData.drawable)
        imageView.setImageDrawable(
            ContextCompat.getDrawable(
                application.applicationContext,
                R.drawable.shape
            )
        )*/
        imageView.setImageDrawable(gradientDrawable)
        favImageView.setImageResource(if (itemMovieData.isFavorite) R.drawable.ic_baseline_favorite_true else R.drawable.ic_baseline_favorite_false)

    }

}