package ru.meseen.dev.androidacademy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.data.CastData

class CastAdapter :
    ListAdapter<CastData, RecyclerView.ViewHolder>(MOVIE_DETAIL_COMPARATOR) {

    companion object {
        private val MOVIE_DETAIL_COMPARATOR = object : DiffUtil.ItemCallback<CastData>() {
            override fun areItemsTheSame(oldItem: CastData, newItem: CastData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CastData, newItem: CastData): Boolean {
                return oldItem.name == newItem.name
                        && oldItem.drawableUrl == newItem.drawableUrl
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false)
        val vh = CastViewHolder(view)
        vh.castParent.setOnClickListener {
            Snackbar.make(it, "Actor ${getItem(vh.adapterPosition).name}", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(parent.context, R.color.colorPrimary))
                .show()
        }
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CastViewHolder) {
            holder.bind(getItem(position))
        }
    }


}

class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val castParent: FrameLayout = itemView.findViewById(R.id.castParent)
    private val castView: ImageView = itemView.findViewById(R.id.castImage)
    private val castText: TextView = itemView.findViewById(R.id.castText)

    fun bind(item: CastData) {
        castText.text = item.name

        Glide.with(itemView.context)
            .load(item.drawableUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .placeholder(R.drawable.loading_card_img)
            .error(R.drawable.no_photo)
            .into(castView)

    }

}