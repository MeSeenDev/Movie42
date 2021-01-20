package ru.meseen.dev.androidacademy.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieDetailsViewModel
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieViewModelFactory
import ru.meseen.dev.androidacademy.support.FragmentsTags

class FragmentMoviesDetails : Fragment() {

    private lateinit var navBack: MaterialTextView
    private lateinit var toolBarImage: ImageView

    private lateinit var application: Application

    private val viewModel by activityViewModels<MovieDetailsViewModel> {
        MovieViewModelFactory(owner = this, application, null)
    }

    companion object {
        private const val MOVIE_ITEM_KEY = "MOVIE_ITEM_KEY"
        fun getInstance(application: Application, movieID: Long): Fragment {
            val instance = FragmentMoviesDetails()
            val bundle = Bundle()
            bundle.putLong(MOVIE_ITEM_KEY, movieID)
            instance.arguments = bundle
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
        /* Glide.with(view.context)
             .load(movieData.backdropIMG)
             .centerCrop()
             .error(R.drawable.no_photo)
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .placeholder(R.drawable.loading_card_img)
             .into(toolBarImage)*/

        val recyclerView = view.findViewById<RecyclerView>(R.id.detailRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(application)

    }

}