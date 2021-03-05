package ru.meseen.dev.androidacademy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.vh.MovieItemHolder
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity

class PagingPageAdapter(
    private val listener: MovieClickListener
) :
    PagingDataAdapter<MovieDataEntity, RecyclerView.ViewHolder>(MOVIES_COMPARATOR) {


    companion object {
        const val TAG = "PagingPageAdapter"
        private val MOVIES_COMPARATOR = object : DiffUtil.ItemCallback<MovieDataEntity>() {
            override fun areItemsTheSame(
                oldItem: MovieDataEntity,
                newItem: MovieDataEntity
            ): Boolean {
                return oldItem.description == newItem.description && oldItem.voteCount == newItem.voteCount
                        && oldItem.title == newItem.title && oldItem.listType == newItem.listType
            }

            override fun getChangePayload(
                oldItem: MovieDataEntity,
                newItem: MovieDataEntity
            ): Any? = Any()

            override fun areContentsTheSame(
                oldItem: MovieDataEntity,
                newItem: MovieDataEntity
            ): Boolean = oldItem == newItem


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        val holder = MovieItemHolder(view)
        view.setOnClickListener {
            val position = holder.absoluteAdapterPosition
            val curItem = getItem(position)
            curItem?.let { it1 -> listener.onItemClick(it1.movieId.toInt(), holder.itemView) }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieItemHolder) {
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycle_alpha)
            getItem(position)?.let {
                holder.bind(it)
            }
        }
    }

}

interface MovieClickListener {
    fun onItemClick(movieID: Int, view: View)
}