package ru.meseen.dev.androidacademy.adapter

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
import ru.meseen.dev.androidacademy.data.base.entity.MovieEntity

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var goupView: ConstraintLayout = itemView.findViewById(R.id.mainItemGroup)
    var imageView: ImageView = itemView.findViewById(R.id.imageMainItemView)
    private var keywordsText: TextView = itemView.findViewById(R.id.keywordsText)
    var favImageView: ImageView = itemView.findViewById(R.id.favImageView)
    private var reviewsText: TextView = itemView.findViewById(R.id.reviewsText)
    private var labelMainText: TextView = itemView.findViewById(R.id.labelMainText)
    private var movieLengthText: TextView = itemView.findViewById(R.id.movieLengthText)
    private var pgMainText: TextView = itemView.findViewById(R.id.pgMainText)
    private var ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

    fun bind(itemMovieData: MovieEntity, application: Application) {
        var temp = "${itemMovieData.pgRating}+"
        pgMainText.text = temp
        labelMainText.text = itemMovieData.labelText
        ratingBar.rating = itemMovieData.ratings
        temp = "${itemMovieData.reviewsText} REVIEWS"
        reviewsText.text = temp
        temp = itemMovieData.genreData.joinToString { it.name }
        keywordsText.text = temp
        temp = "${itemMovieData.movieLength}MIN"
        movieLengthText.text = temp

        Glide.with(application).load(itemMovieData.posterIMG)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .centerCrop()
            .placeholder(R.drawable.loading_card_img)
            .into(object : CustomViewTarget<ImageView, Drawable>(imageView) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    imageView.background = resource
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    imageView.background =
                        ContextCompat.getDrawable(application, R.drawable.no_photo)
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                    imageView.background = placeholder
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

        imageView.setImageDrawable(gradientDrawable)
        favImageView.setImageResource(if (itemMovieData.isFavorite) R.drawable.ic_baseline_favorite_true else R.drawable.ic_baseline_favorite_false)

    }

}