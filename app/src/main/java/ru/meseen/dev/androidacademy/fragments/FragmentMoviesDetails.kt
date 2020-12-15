package ru.meseen.dev.androidacademy.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textview.MaterialTextView
import ru.meseen.dev.androidacademy.FragmentsTags
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapter.ListMassiveAdapter
import ru.meseen.dev.androidacademy.data.DataKeys
import ru.meseen.dev.androidacademy.data.entity.MovieEntity

class FragmentMoviesDetails : Fragment() {

    private lateinit var navBack: MaterialTextView
    private lateinit var toolBarImage: ImageView

    private lateinit var application: Application
    private lateinit var movieData: MovieEntity

    companion object {
        private const val MOVIE_ITEM_KEY = "MOVIE_ITEM_KEY"
        fun getInstance(application: Application, movieData: MovieEntity): Fragment {
            val instance = FragmentMoviesDetails()
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_ITEM_KEY, movieData)
            instance.arguments = bundle
            instance.application = application
            return instance
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        movieData = arguments?.getParcelable(MOVIE_ITEM_KEY) ?: MovieEntity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navBack = view.findViewById(R.id.home)
        navBack.setOnClickListener {
            parentFragmentManager.popBackStack(
                FragmentsTags.MOVIE_LIST_TAG.toString(),
                POP_BACK_STACK_INCLUSIVE
            )
        }
        toolBarImage = view.findViewById(R.id.app_bar_image)
        Glide.with(view.context).load(movieData.backdropIMG).centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(toolBarImage)

        val recyclerView = view.findViewById<RecyclerView>(R.id.detailRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(application)
        val adapter = ListMassiveAdapter(null, application, DataKeys.DETAILS_TYPE)
        movieData.let {
            adapter.submitList(listOf(it))
        }
        recyclerView.adapter = adapter

    }

}