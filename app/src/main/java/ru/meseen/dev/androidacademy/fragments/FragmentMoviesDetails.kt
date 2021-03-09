package ru.meseen.dev.androidacademy.fragments

import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.MovieDetailAdapter
import ru.meseen.dev.androidacademy.data.base.entity.MovieAdditionalDataEntity
import ru.meseen.dev.androidacademy.data.base.query.impl.MovieItemQuery
import ru.meseen.dev.androidacademy.data.retrofit.Banner
import ru.meseen.dev.androidacademy.data.retrofit.ConnectionObserver
import ru.meseen.dev.androidacademy.data.retrofit.ConnectionState
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieDetailsViewModel
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieViewModelFactory
import ru.meseen.dev.androidacademy.support.FragmentsTags
import ru.meseen.dev.androidacademy.support.PreferencesKeys

@ExperimentalPagingApi
@ExperimentalSerializationApi
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
        const val TAG = "MovieDetailsViewModel" //TODO  !!!


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

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        Log.d(TAG, "setupDialog: $dialog  $style")
        super.setupDialog(dialog, style)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        @Suppress("DEPRECATION")// киборги.
        retainInstance = true
        movieID = arguments?.getInt(MOVIE_ITEM_KEY) ?: -1
        parentFragmentsTag =
            arguments?.getString(PARENT_FRAG_TAG) ?: FragmentsTags.MOVIE_DETAILS_TAG.toString()

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.activity_main_frame
            duration = 300L
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(
                ResourcesCompat.getColor(
                    application.resources,
                    R.color.background,
                    null
                )
            )
        }


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
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backCallback
        )
        toolBarImage = view.findViewById(R.id.app_bar_image)
        toolBarImage.background = null
        navBack = view.findViewById(R.id.home)
        navBack.setOnClickListener {
            backCallback.handleOnBackPressed()
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.detailRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        adapter = MovieDetailAdapter()
        recyclerView.adapter = adapter

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(application)
        val languageQuery = sharedPrefs.getString(
            PreferencesKeys.LANGUAGE_KEY.key,
            PreferencesKeys.LANGUAGE_KEY.defaultKey
        ) ?: PreferencesKeys.LANGUAGE_KEY.defaultKey

        lifecycleScope.launchWhenStarted {
            viewModel.loadMovieItem(
                MovieItemQuery(
                    movieID, language = languageQuery
                )
            ).collectLatest {
                Log.d(TAG, "onViewCreated: $it")
                adapter.submitList(it)
                val movieItem = it.find { movie -> movie is MovieAdditionalDataEntity }
                movieItem?.let { item ->
                    item as MovieAdditionalDataEntity
                    setBackImage(RetrofitClient.getImageUrl(item.backdropPath))

                }
            }
        }
        initTextBanner(view)

    }
    private fun initTextBanner(view: View){
        val textBanner = Banner(view.findViewById(R.id.status_text_view_search))
        lifecycleScope.launchWhenCreated {
            ConnectionObserver(view.context).observe(viewLifecycleOwner){
                textBanner.submitState(it)
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


    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (parentFragmentsTag == FragmentsTags.MOVIE_LIST_TAG.toString()) {
                parentFragmentManager.popBackStack(
                    parentFragmentsTag,
                    POP_BACK_STACK_INCLUSIVE
                )
            } else {
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        viewModel.clearList()
        super.onDestroy()
    }
}