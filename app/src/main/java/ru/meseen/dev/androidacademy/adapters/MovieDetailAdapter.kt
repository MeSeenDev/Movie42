package ru.meseen.dev.androidacademy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.vh.CastListViewHolder
import ru.meseen.dev.androidacademy.adapters.vh.DetailViewHolder
import ru.meseen.dev.androidacademy.data.base.entity.MovieAdditionalDataEntity
import ru.meseen.dev.androidacademy.data.base.entity.relations.CastList
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieDetailViewItems


class MovieDetailAdapter :
    ListAdapter<MovieDetailViewItems, RecyclerView.ViewHolder>(MOVIE_ITEM_COMPARE) {

    companion object {
        const val MAIN_VIEW_TYPE = 1
        const val CAST_VIEW_TYPE = 2

        private val MOVIE_ITEM_COMPARE = object : DiffUtil.ItemCallback<MovieDetailViewItems>() {

            override fun areItemsTheSame(
                oldItem: MovieDetailViewItems,
                newItem: MovieDetailViewItems
            ): Boolean =
                when (oldItem) {
                    is MovieAdditionalDataEntity -> {
                        if (newItem is MovieAdditionalDataEntity) {
                            oldItem.id == newItem.id
                        } else {
                            false
                        }
                    }
                    is CastList -> {
                        if (newItem is CastList) {
                            oldItem.castList.first().castId == newItem.castList.first().castId
                        } else {
                            false
                        }
                    }
                    else -> false
                }


            override fun areContentsTheSame(
                oldItem: MovieDetailViewItems,
                newItem: MovieDetailViewItems
            ): Boolean = when (oldItem) {
                is MovieAdditionalDataEntity -> {
                    if (newItem is MovieAdditionalDataEntity) {
                        oldItem.id == newItem.id && oldItem.overview == newItem.overview
                    } else {
                        false
                    }
                }
                is CastList -> {
                    if (newItem is CastList) {
                        oldItem.castList == newItem.castList
                    } else {
                        false
                    }
                }
                else -> false
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == MAIN_VIEW_TYPE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
            DetailViewHolder(view)
        } else {
            val castView =
                LayoutInflater.from(parent.context).inflate(R.layout.main_item_cast, parent, false)
            CastListViewHolder(castView)
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DetailViewHolder) {
            val item = currentList.find { it is MovieAdditionalDataEntity }
            item?.let {
                holder.bind(it as MovieAdditionalDataEntity)
            }
        } else if (holder is CastListViewHolder) {
            val item = currentList.find { it is CastList }
            item?.let {
                holder.bind(it as CastList)
            }
        }
    }


    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is MovieAdditionalDataEntity -> MAIN_VIEW_TYPE
        is CastList -> CAST_VIEW_TYPE
        else -> -1
    }

}