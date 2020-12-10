package ru.meseen.dev.androidacademy.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.data.DataKeys
import ru.meseen.dev.androidacademy.data.base.App
import ru.meseen.dev.androidacademy.data.entity.MovieEntity

class ListMassiveAdapter(
    private val listener: RecycleClickListener?,
    private val application: Application,
    private val viewType: DataKeys
) : ListAdapter<MovieEntity, RecyclerView.ViewHolder>(MOVIE_COMPARATOR) {

    private val scope = (application as App).applicationScope
    private val repository = (application as App).repository

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == DataKeys.MAIN_TYPE.num) {
            val mainViewHolder = MainViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.main_item, parent, false))

            mainViewHolder.favImageView.setOnClickListener {
                val position = mainViewHolder.adapterPosition
                val curItem = getItem(position)
                setFavoriteState(curItem, mainViewHolder)
            }
            mainViewHolder.goupView.setOnClickListener {
                listener?.onClick(getItem(mainViewHolder.adapterPosition))
            }
            return mainViewHolder
        }
        return DetailViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false))
    }


    /**
     * @param curItem Current Item
     * @param mainViewHolder ViewHolder for item
     */
    private fun setFavoriteState(
        curItem: MovieEntity,
        mainViewHolder: MainViewHolder
    ) {
        val favImage: Int = when (curItem.isFavorite) {
            true -> {
                curItem.isFavorite = false
                R.drawable.ic_baseline_favorite_false
            }
            false -> {
                curItem.isFavorite = true
                R.drawable.ic_baseline_favorite_true
            }
        }
        mainViewHolder.favImageView.setImageResource(favImage)
        insertItemToBD(curItem)
    }


    private fun insertItemToBD(item: MovieEntity) {
        scope.launch {
            repository.insert(item)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //В принцире разделение в данном контексте не нужно
        when (holder) {
            is MainViewHolder -> holder.bind(getItem(position), application)
            is DetailViewHolder -> holder.bind(getItem(position), application)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (viewType == DataKeys.MAIN_TYPE) {
            return DataKeys.MAIN_TYPE.num
        }
        return DataKeys.DATA_KEY.num
    }

    interface RecycleClickListener {
        fun onClick(item: MovieEntity)
    }


    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}

