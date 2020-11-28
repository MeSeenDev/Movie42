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
import com.google.android.material.textview.MaterialTextView
import ru.meseen.dev.androidacademy.Helper.MOVIE_LIST
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapter.MassiveAdapter
import ru.meseen.dev.androidacademy.data.DataKeys
import ru.meseen.dev.androidacademy.data.MovieData

class FragmentMoviesDetails() : Fragment() {

    private lateinit var navBack: MaterialTextView
    private lateinit var toolBarImage: ImageView

    private lateinit var application: Application
    private lateinit var movieData: MovieData

    companion object {
        private const val MOVIE_ITEM_KEY = "MOVIE_ITEM_KEY"
        fun getInstance(application: Application, movieData: MovieData): Fragment {
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
        movieData = arguments?.getParcelable<MovieData>(MOVIE_ITEM_KEY) ?: MovieData()
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
                fragmentManager?.popBackStack(MOVIE_LIST.toString(),POP_BACK_STACK_INCLUSIVE)
        }
        toolBarImage = view.findViewById(R.id.app_bar_image)
        val recyclerView = view.findViewById<RecyclerView>(R.id.detailRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(application)
        val adapter = MassiveAdapter(listOf(movieData), null, application, DataKeys.DETAILS_TYPE)
        recyclerView.adapter = adapter

    }
}