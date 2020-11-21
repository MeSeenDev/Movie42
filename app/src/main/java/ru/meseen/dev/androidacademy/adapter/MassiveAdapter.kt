package ru.meseen.dev.androidacademy.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.data.DataKeys
import ru.meseen.dev.androidacademy.data.MovieData

class MassiveAdapter(
    private var list: List<MovieData> = listOf(),
    private val listener: RecycleClickListener?,
    private val application: Application,
    private val viewType: DataKeys
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == DataKeys.MAIN_TYPE.num) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_item, parent, false)
            val mainViewHolder = MainViewHolder(view)
            mainViewHolder.favImageView.setOnClickListener {
                mainViewHolder.favImageView.setImageResource(R.drawable.ic_baseline_favorite_true)
            }
            mainViewHolder.goupView.setOnClickListener {
                listener?.onClick(list[mainViewHolder.adapterPosition])
            }
            return mainViewHolder
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)

        return DetailViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainViewHolder) {
            val itemMovieData = list[position]
            var temp = "${itemMovieData.pg}+"
            holder.pgMainText.text = temp
            holder.labelMainText.text = itemMovieData.labelText
            holder.ratingBar.rating = itemMovieData.reviewsStars.toFloat()
            holder.reviewsText.text = itemMovieData.reviewsText
            holder.keywordsText.text = itemMovieData.keywordsText
            temp = "${itemMovieData.movieLength}MIN"
            holder.movieLengthText.text = temp
            holder.imageView.background =
                ContextCompat.getDrawable(application.applicationContext, itemMovieData.drawable)
            holder.imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    application.applicationContext,
                    R.drawable.shape
                )
            )
            holder.favImageView.setImageResource(if (itemMovieData.isFavorite) R.drawable.ic_baseline_favorite_true else R.drawable.ic_baseline_favorite_24)
        }

        if (holder is DetailViewHolder) {
            val itemMovieData = list[position]
            var temp = itemMovieData.labelText.replaceFirst(" ", "\n")
            holder.labelText.text = temp
            temp = itemMovieData.pg.toString() + "+"
            holder.pgMainText.text = temp
            holder.descriptionText.text = itemMovieData.descriptionText
            holder.keywordsText.text = itemMovieData.keywordsText
            holder.reviewsText.text = itemMovieData.reviewsText
            holder.ratingBar.rating = itemMovieData.reviewsStars.toFloat()
            holder.reviewsText.text = itemMovieData.reviewsText

            holder.recycleCast.adapter = CastAdapter(itemMovieData.cast, application)
            holder.recycleCast.layoutManager =
                LinearLayoutManager(application.baseContext, LinearLayoutManager.HORIZONTAL, false)


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface RecycleClickListener {
        fun onClick(item: MovieData)
    }


    override fun getItemViewType(position: Int): Int {
        if (viewType == DataKeys.MAIN_TYPE) {
            return DataKeys.MAIN_TYPE.num
        }
        return DataKeys.DATA_KEY.num
    }
}