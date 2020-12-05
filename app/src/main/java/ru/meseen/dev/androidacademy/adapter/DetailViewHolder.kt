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

    var pgMainText: TextView = itemView.findViewById(R.id.pgText)
    var labelText: TextView = itemView.findViewById(R.id.labelText)
    var reviewsText: TextView = itemView.findViewById(R.id.reviewsText)
    var keywordsText: TextView = itemView.findViewById(R.id.keywordsText)
    var descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
    var recycleCast: RecyclerView = itemView.findViewById(R.id.recycleCast)
    var ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

    fun bind(itemMovieData: MovieEntity, application: Application) {
        var temp = itemMovieData.labelText.replaceFirst(" ", "\n")
        labelText.text = temp
        temp = itemMovieData.pg.toString() + "+"
        pgMainText.text = temp
        descriptionText.text = itemMovieData.descriptionText
        keywordsText.text = itemMovieData.keywordsText
        reviewsText.text = itemMovieData.reviewsText
        ratingBar.rating = itemMovieData.reviewsStars.toFloat()
        reviewsText.text = itemMovieData.reviewsText

        val adapter = CastAdapter()
        itemMovieData.cast.let {
            adapter.submitList(it)
        }
        recycleCast.adapter = adapter
        recycleCast.layoutManager =
            LinearLayoutManager(application.baseContext, LinearLayoutManager.HORIZONTAL, false)
    }


}