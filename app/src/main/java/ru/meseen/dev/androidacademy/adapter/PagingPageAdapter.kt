package ru.meseen.dev.androidacademy.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapter.vh.MovieItemHolder
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity

class PagingPageAdapter(
    private val application: Application,
    private val listener: MovieClickListener
) :
    PagingDataAdapter<MovieDataEntity, RecyclerView.ViewHolder>(MOVIES_COMPARATOR) {


    companion object {
        private val MOVIES_COMPARATOR = object : DiffUtil.ItemCallback<MovieDataEntity>() {
            override fun areItemsTheSame(
                oldItem: MovieDataEntity,
                newItem: MovieDataEntity
            ): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(
                oldItem: MovieDataEntity,
                newItem: MovieDataEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        val holder = MovieItemHolder(view)
        view.setOnClickListener{
            val position = holder.absoluteAdapterPosition
            val curItem = getItem(position)
            curItem?.let { it1 -> listener.onItemClick(it1.movieId) }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MovieItemHolder) {
            getItem(position)?.let { holder.bind(it, application = application) }
        }
    }

}

interface MovieClickListener {
    fun onItemClick(movieID: Long)
}