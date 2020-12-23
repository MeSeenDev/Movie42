package ru.meseen.dev.androidacademy.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapter.ListMassiveAdapter
import ru.meseen.dev.androidacademy.data.base.App
import ru.meseen.dev.androidacademy.data.base.entity.MovieEntity
import ru.meseen.dev.androidacademy.data.viewmodel.MovieViewModel
import ru.meseen.dev.androidacademy.data.viewmodel.MovieViewModelFactory
import ru.meseen.dev.androidacademy.support.DataKeys
import ru.meseen.dev.androidacademy.support.FragmentsTags.MOVIE_DETAILS_TAG
import ru.meseen.dev.androidacademy.support.FragmentsTags.MOVIE_LIST_TAG

class FragmentMoviesList : Fragment(), ListMassiveAdapter.RecycleClickListener {

    private val movieViewModel: MovieViewModel by activityViewModels {
        MovieViewModelFactory((application as App).repository)
    }

    private lateinit var application: Application

    companion object {
        fun getInstance(
            application: Application
        ): Fragment {
            val instance = FragmentMoviesList()
            instance.application = application
            return instance
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

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
        recyclerView.setHasFixedSize(true)
        val adapter = ListMassiveAdapter(this, application, DataKeys.MAIN_TYPE)
        movieViewModel.allWords.observe(viewLifecycleOwner, {
            it.let {
                adapter.submitList(it)
            }
        })
        recyclerView.adapter = adapter
        val gridLayoutManager: RecyclerView.LayoutManager
        val quantity =
            (resources.displayMetrics.widthPixels /
                    (resources.getDimension(R.dimen.main_image_view_item_width) + resources.getDimension(
                        R.dimen.main_item_margins
                    )).toInt())

        gridLayoutManager = GridLayoutManager(application.baseContext, quantity)
        gridLayoutManager.isUsingSpansToEstimateScrollbarDimensions = true
        recyclerView.layoutManager = gridLayoutManager


    }

    override fun onClick(item: MovieEntity) {
        val fragmentManager = parentFragmentManager.beginTransaction()
        fragmentManager.replace(
            R.id.activity_main_frame,
            FragmentMoviesDetails.getInstance(application, movieData = item),
            MOVIE_DETAILS_TAG.toString()
        ).addToBackStack(MOVIE_LIST_TAG.toString())
            .setTransition(TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

}