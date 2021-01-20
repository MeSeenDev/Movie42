@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused")

package ru.meseen.dev.androidacademy.adapter.vh

import android.app.Application
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient.getImageUrl
import kotlin.math.round

class MovieItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var groupView: ConstraintLayout = itemView.findViewById(R.id.mainItemGroup)
    var posterImage: ImageView = itemView.findViewById(R.id.posterImage)
    private var genres: TextView = itemView.findViewById(R.id.genresKeywordsText)
    var favImageView: ImageView = itemView.findViewById(R.id.favImageView)
    private var reviewsText: TextView = itemView.findViewById(R.id.reviewsText)
    private var labelMainText: TextView = itemView.findViewById(R.id.labelMainText)
    private var runtime: TextView = itemView.findViewById(R.id.runtimeText)
    private var pgMainText: TextView = itemView.findViewById(R.id.pgMainText)
    private var ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

    fun bind(itemMovieData: MovieDataEntity, application: Application) {

        var temp = itemMovieData.genreIds
        genres.text = temp
        temp = "${itemMovieData.voteCount} REVIEWS"
        reviewsText.text = temp
        temp = "99MIN"
        runtime.visibility = View.INVISIBLE
        pgMainText.text = if (itemMovieData.adult) "+16" else "+12"
        labelMainText.text = itemMovieData.title
        ratingBar.rating = round(itemMovieData.voteAverage.toFloat() / 2)

        loadImage(itemMovieData, application)


    }

    private fun loadImage(itemMovieData: MovieDataEntity, application: Application) {
        Glide.with(application)
            .load(getImageUrl(itemMovieData.posterPath))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .centerCrop()
            .placeholder(R.drawable.loading_card_img)
            .into(object : CustomViewTarget<ImageView, Drawable>(posterImage) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    posterImage.background = resource
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    posterImage.background =
                        ContextCompat.getDrawable(application, R.drawable.no_photo)
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                    posterImage.background = placeholder
                }
            })

        val start = ContextCompat.getColor(application, R.color.start_of_shape)
        val center = ContextCompat.getColor(application, R.color.center_of_shape)
        val end = ContextCompat.getColor(application, R.color.end_of_shape)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            intArrayOf(start, center, end)
        )
        gradientDrawable.setGradientCenter(0.6f, 0.5f)
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
    }

}