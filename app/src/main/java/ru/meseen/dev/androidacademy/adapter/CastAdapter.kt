package ru.meseen.dev.androidacademy.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.data.CastData

class CastAdapter(private val list: List<CastData>, private val application: Application) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false)
        val vh = CastViewHolder(view)
        vh.castParent.setOnClickListener {
            Snackbar.make(it, "Работает ${list[vh.adapterPosition].name}", Snackbar.LENGTH_SHORT)
                .show()
        }
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CastViewHolder) {
            val item = list[position]
            holder.castText.text = item.name
            holder.castView.setImageResource(item.drawable)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val castParent: FrameLayout = itemView.findViewById(R.id.castParent)
    val castView: ImageView = itemView.findViewById(R.id.castImage)
    val castText: TextView = itemView.findViewById(R.id.castText)
}