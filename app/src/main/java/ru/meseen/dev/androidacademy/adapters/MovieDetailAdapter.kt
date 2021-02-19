package ru.meseen.dev.androidacademy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.vh.DetailViewHolder
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity


class MovieDetailAdapter :
    ListAdapter<MovieItemEntity, RecyclerView.ViewHolder>(MOVIE_ITEM_COMPARE) {

    companion object {
        private val MOVIE_ITEM_COMPARE = object : DiffUtil.ItemCallback<MovieItemEntity>() {

            override fun areItemsTheSame(
                oldItem: MovieItemEntity,
                newItem: MovieItemEntity
            ): Boolean = oldItem.castList == newItem.castList &&
                    oldItem.genresEntity == newItem.genresEntity &&
                    oldItem.movieAddData == oldItem.movieAddData



            override fun areContentsTheSame(
                oldItem: MovieItemEntity,
                newItem: MovieItemEntity
            ): Boolean = oldItem == newItem


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if(holder is DetailViewHolder){
           holder.bind(getItem(position))
       }
    }
}