package ru.meseen.dev.androidacademy.adapters.loaderStates

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.PagingPageAdapter
import ru.meseen.dev.androidacademy.fragments.FragmentMoviesList

class FooterLoadStateAdapter (private val adapter: PagingPageAdapter) :
    LoadStateAdapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_load_state, parent, false)
        return FooterLoadViewHolder(view) { adapter.retry() }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {
        if (holder is FooterLoadViewHolder) {
            holder.bind(loadState)
        }
    }

}

class FooterLoadViewHolder(view: View, private val retryCallback: () -> Unit) :
    RecyclerView.ViewHolder(view) {

    private val infoText = view.findViewById<MaterialTextView>(R.id.loadStateMessage)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    private val retryButton = view.findViewById<MaterialButton>(R.id.loadStateRetryBtn)
        .also {
            it.setOnClickListener { retryCallback() }
        }

    fun bind(loadState: LoadState) {
        progressBar.isVisible = false
        retryButton.isVisible = loadState is Error
        infoText.isVisible =
            !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        infoText.text = (loadState as? LoadState.Error)?.error?.message
    }

}