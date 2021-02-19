package ru.meseen.dev.androidacademy.adapters.vh

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.CastAdapter
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity
import java.util.*
import kotlin.math.floor

class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var pgMainText: TextView = itemView.findViewById(R.id.pgText)
    private var labelText: TextView = itemView.findViewById(R.id.labelText)
    private var reviewsText: TextView = itemView.findViewById(R.id.reviewsText)
    private var keywordsText: TextView = itemView.findViewById(R.id.genresKeywordsText)
    private var descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
    private var recycleCast: RecyclerView = itemView.findViewById(R.id.recycleCast)
    private var ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)


    fun bind(movieItemEntity: MovieItemEntity) {
        val mainItem = movieItemEntity.movieAddData
        val genresItems = movieItemEntity.genresEntity
        val castItems = movieItemEntity.castList

        pgMainText.text = if (mainItem.adult) "+16" else "+12"
        labelText.text = mainItem.title

        val voteCount = mainItem.voteCount
        val reviewsCount = itemView.context.resources.getString(R.string.reviews)
        val reviewsVoteText = "$voteCount $reviewsCount"
        reviewsText.text = reviewsVoteText
        ratingBar.rating = floor(mainItem.voteAverage / 2).toFloat()
        descriptionText.text = mainItem.overview

        keywordsText.text = genresItems.joinToString { it.genresName }.capitalize(Locale.ROOT)

        val adapter = CastAdapter()
        recycleCast.adapter = adapter
        val linearLayoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        recycleCast.layoutManager = linearLayoutManager
        adapter.submitList(castItems)

    }


}