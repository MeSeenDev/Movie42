package ru.meseen.dev.androidacademy.adapter

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R

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

}