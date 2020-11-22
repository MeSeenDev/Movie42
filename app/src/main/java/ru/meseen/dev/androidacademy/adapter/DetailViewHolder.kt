package ru.meseen.dev.androidacademy.adapter

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R

class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var pgMainText: TextView = itemView.findViewById(R.id.pgText)
    var labelText: TextView = itemView.findViewById(R.id.labelText)
    var reviewsText: TextView = itemView.findViewById(R.id.reviewsText)
    var keywordsText: TextView = itemView.findViewById(R.id.keywordsText)
    var descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
    var recycleCast: RecyclerView = itemView.findViewById(R.id.recycleCast)
    var ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

}