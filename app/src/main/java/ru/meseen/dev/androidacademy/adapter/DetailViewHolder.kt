package ru.meseen.dev.androidacademy.adapter

import android.app.Application
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.data.entity.MovieEntity

class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var pgMainText: TextView = itemView.findViewById(R.id.pgText)
    private var labelText: TextView = itemView.findViewById(R.id.labelText)
    private var reviewsText: TextView = itemView.findViewById(R.id.reviewsText)
    private var keywordsText: TextView = itemView.findViewById(R.id.keywordsText)
    private var descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
    private var recycleCast: RecyclerView = itemView.findViewById(R.id.recycleCast)
    private var ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)


    fun bind(itemMovieData: MovieEntity, application: Application) {
        var temp: String = if (itemMovieData.labelText.split(" ").size > 1) {
            itemMovieData.labelText.replaceFirst(" ", "\n")
        } else {
            itemMovieData.labelText
        }
        labelText.text = temp
        temp = itemMovieData.PgRating.toString() + "+"
        pgMainText.text = temp
        descriptionText.text = itemMovieData.descriptionText
        temp = itemMovieData.genres.joinToString { it.name }
        keywordsText.text = temp
        temp = "${itemMovieData.reviewsText} REVIEWS"
        reviewsText.text = temp

        ratingBar.rating = itemMovieData.ratings

        val adapter = CastAdapter()
        itemMovieData.cast.let {
            adapter.submitList(it)
        }
        recycleCast.adapter = adapter
        recycleCast.layoutManager =
            LinearLayoutManager(application.baseContext, LinearLayoutManager.HORIZONTAL, false)
    }


}