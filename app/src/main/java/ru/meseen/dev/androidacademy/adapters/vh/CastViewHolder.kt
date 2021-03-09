package ru.meseen.dev.androidacademy.adapters.vh

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.CastAdapter
import ru.meseen.dev.androidacademy.data.base.entity.relations.CastList

class CastListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var recycleCast: RecyclerView = itemView.findViewById(R.id.recycleCast)

    fun bind(castList: CastList) {
        val casts = castList.castList
        val adapter = CastAdapter()
        recycleCast.adapter = adapter
        val linearLayoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        recycleCast.layoutManager = linearLayoutManager
        adapter.submitList(casts)
    }
}