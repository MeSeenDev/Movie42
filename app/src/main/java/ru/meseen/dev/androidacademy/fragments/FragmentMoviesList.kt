package ru.meseen.dev.androidacademy.fragments

import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.Helper.MOVIE_DETAILS
import ru.meseen.dev.androidacademy.Helper.MOVIE_LIST
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapter.MainItemDecorator
import ru.meseen.dev.androidacademy.adapter.MassiveAdapter
import ru.meseen.dev.androidacademy.data.DataKeys
import ru.meseen.dev.androidacademy.data.MovieData

class FragmentMoviesList() : Fragment(), MassiveAdapter.RecycleClickListener {

    private var moviesList: ArrayList<MovieData> = ArrayList<MovieData>()
    private lateinit var application: Application

    companion object {
        private const val MOVIE_LIST_KEY = "MOVIE_LIST_KEY"
        fun getInstance(application: Application, list: ArrayList<MovieData>): Fragment {
            val instance = FragmentMoviesList()
            val bundle = Bundle()
            bundle.putParcelableArrayList(MOVIE_LIST_KEY, list)
            instance.arguments = bundle
            instance.application = application
            return instance
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        moviesList = arguments?.getParcelableArrayList(MOVIE_LIST_KEY) ?: ArrayList()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleMain)
        val adapter = MassiveAdapter(moviesList, this, application, DataKeys.MAIN_TYPE)
        recyclerView.adapter = adapter
        val gridLayoutManager: RecyclerView.LayoutManager

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = GridLayoutManager(application.baseContext, 2)
            gridLayoutManager.isUsingSpansToEstimateScrollbarDimensions = true


        } else {
            gridLayoutManager = LinearLayoutManager(application.baseContext, HORIZONTAL, false)

        }
        recyclerView.addItemDecoration(
            MainItemDecorator(
                resources.getDimension(R.dimen.main_item_decoration_size).toInt()
            )
        )
        recyclerView.layoutManager = gridLayoutManager


    }

    override fun onClick(item: MovieData) {
        val childFrManager = fragmentManager?.beginTransaction()
        childFrManager?.replace(
            R.id.activity_main_frame,
            FragmentMoviesDetails.getInstance(application, movieData = item),
            MOVIE_DETAILS.toString()
        )
        childFrManager?.addToBackStack(MOVIE_LIST.toString())
            ?.setTransition(TRANSIT_FRAGMENT_OPEN)
            ?.commit()
    }
}