package ru.meseen.dev.androidacademy.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textview.MaterialTextView
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.MovieDetailAdapter
import ru.meseen.dev.androidacademy.data.base.query.impl.MovieItemQuery
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieDetailsViewModel
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieViewModelFactory
import ru.meseen.dev.androidacademy.support.FragmentsTags


class FragmentMoviesDetails : BottomSheetDialogFragment() {

    private lateinit var navBack: MaterialTextView
    private lateinit var toolBarImage: ImageView

    private lateinit var application: Application
    private var movieID: Int = -1

    private lateinit var adapter: MovieDetailAdapter
    private lateinit var parentFragmentsTag: String

    private val viewModel by activityViewModels<MovieDetailsViewModel> {
        MovieViewModelFactory(owner = this, application, null)
    }

    companion object {
        private const val MOVIE_ITEM_KEY = "MOVIE_ITEM_KEY"
        private const val PARENT_FRAG_TAG = "PARENT_FRAG_TAG"
        fun getInstance(
            application: Application,
            movieID: Int,
            parentFragment: FragmentsTags
        ): Fragment {
            val instance = FragmentMoviesDetails()
            val bundle = Bundle()
            bundle.putInt(MOVIE_ITEM_KEY, movieID)
            bundle.putString(PARENT_FRAG_TAG, parentFragment.toString())
            instance.arguments = bundle
            instance.application = application
            return instance
        }

    }

    @Suppress("DEPRECATION")// киборги.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        movieID = arguments?.getInt(MOVIE_ITEM_KEY) ?: -1
        parentFragmentsTag =
            arguments?.getString(PARENT_FRAG_TAG) ?: FragmentsTags.MOVIE_DETAILS_TAG.toString()


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)
        val windowHeight: Int =
            view.context.resources.displayMetrics.heightPixels
        val parentSheet = view.findViewById<NestedScrollView>(R.id.parentDetails)
        val layoutParams = parentSheet.layoutParams

        layoutParams.height = windowHeight
        parentSheet.layoutParams = layoutParams

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBarImage = view.findViewById(R.id.app_bar_image)
        toolBarImage.background = null
        navBack = view.findViewById(R.id.home)
        navBack.setOnClickListener {

            parentFragmentManager.popBackStack(
                parentFragmentsTag,
                POP_BACK_STACK_INCLUSIVE
            )
            dismiss()

        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.detailRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        adapter = MovieDetailAdapter()
        recyclerView.adapter = adapter
        viewModel.loadMovieItem(
            MovieItemQuery(
                movieID, language = "ru-RU"
            )
        ).observe(viewLifecycleOwner) { item ->
            item?.let {
                adapter.submitList(listOf(it))
                setBackImage(RetrofitClient.getImageUrl(it.movieAddData.backdropPath))
            }
        }

    }


    private fun setBackImage(url: String) {
        view?.let {
            Glide.with(it.context)
                .load(url)
                .centerCrop()
                .error(R.drawable.no_photo)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.loading_card_img)
                .into(toolBarImage)
        }
    }

    override fun onDestroyView() {
        viewModel.clear()
        super.onDestroyView()
    }
}