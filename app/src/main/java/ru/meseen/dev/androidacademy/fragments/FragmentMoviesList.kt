package ru.meseen.dev.androidacademy.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.RESTART
import android.app.Application
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.MovieClickListener
import ru.meseen.dev.androidacademy.adapters.PagingPageAdapter
import ru.meseen.dev.androidacademy.adapters.loaderStates.FooterLoadStateAdapter
import ru.meseen.dev.androidacademy.data.retrofit.Banner
import ru.meseen.dev.androidacademy.data.retrofit.ConnectionObserver
import ru.meseen.dev.androidacademy.data.retrofit.ConnectionState
import ru.meseen.dev.androidacademy.data.retrofit.MaterialTextBanner
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieViewModel
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieViewModelFactory
import ru.meseen.dev.androidacademy.support.FragmentsTags.MOVIE_DETAILS_TAG
import ru.meseen.dev.androidacademy.support.FragmentsTags.MOVIE_LIST_TAG
import ru.meseen.dev.androidacademy.support.ListType
import ru.meseen.dev.androidacademy.support.ListType.*
import ru.meseen.dev.androidacademy.support.PreferencesKeys.LANGUAGE_KEY
import ru.meseen.dev.androidacademy.support.PreferencesKeys.REGION_KEY


@ExperimentalSerializationApi
@ExperimentalPagingApi
class FragmentMoviesList : Fragment(), MovieClickListener {

    companion object {

        private const val KEY_TOOLBAR_TITLE = "KEY_TOOLBAR_TITLE"
        private const val TAG = "FragmentMoviesList"
        fun getInstance(
            application: Application
        ): Fragment {
            val instance = FragmentMoviesList()
            instance.application = application
            return instance
        }
    }

    private val viewModel: MovieViewModel by activityViewModels {
        MovieViewModelFactory(
            application = application,
            owner = this
        )
    }

    private lateinit var application: Application
    private lateinit var toolbar: Toolbar


    private var listType: ListType? = TOP_VIEW_LIST
    private lateinit var adapter: PagingPageAdapter
    private lateinit var footerAdapter: FooterLoadStateAdapter
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var patentLayout: CoordinatorLayout
    private lateinit var statusTextView: MaterialTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            listType =
                savedInstanceState.getSerializable(
                    KEY_TOOLBAR_TITLE
                ) as ListType?
        }
        @Suppress("DEPRECATION")
        retainInstance = true
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application).apply {
            registerOnSharedPreferenceChangeListener(prefsChangeListener)
        }
        exitTransition = MaterialElevationScale(false).apply {
            duration = 300L
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = 300L
        }

    }


    private fun setToolbarTitle(listType: ListType) {
        this.listType = listType
        toolbar.title = listType.getLocalizedName(resources)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        patentLayout = view.findViewById(R.id.list_parent)
        toolbar = view.findViewById(R.id.movies_list_toolbar)
        statusTextView = view.findViewById(R.id.status_text_view)
        listType?.let { setToolbarTitle(it) }
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        return view
    }

    var quantity: Int = 2


    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleMain)
        recyclerView.setHasFixedSize(true)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        quantity =
            view.resources.getInteger(R.integer.main_item_card_span) //todo возможно сделать глобальной
        adapter = PagingPageAdapter(listener = this)
        footerAdapter = FooterLoadStateAdapter(adapter) // ws 11

        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        bottomNavView = view.findViewById(R.id.bottom_navigation)

        bottomNavView.setOnNavigationItemSelectedListener(navigationListener)


        recyclerView.adapter = adapter.withLoadStateFooter(footerAdapter)

        val gridLayoutManager = GridLayoutManager(view.context, quantity)
        gridLayoutManager.isUsingSpansToEstimateScrollbarDimensions = true
        gridLayoutManager.spanSizeLookup = spanSizeLookup

        recyclerView.layoutManager = gridLayoutManager

        swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.movies.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerView.scrollToPosition(0) } ///Обновляшка и сбрасовашка
        }
        val banner = Banner(statusTextView) as MaterialTextBanner

        lifecycleScope.launchWhenStarted {
            ConnectionObserver(application).observe(viewLifecycleOwner) {
                banner.submitState(it)
            }

        }

        // отслеживание состояний и вывод предупреждений, не особо удобно для обработки ошибок сети будут LoadStateAdapters
        lifecycleScope.launchWhenCreated {
            adapter.addLoadStateListener {
                val loadState = it.refresh
                val loadStateMediator = it.mediator
                if (loadState is LoadState.Error) {
                    banner.submitState(
                        ConnectionState
                            .Error
                            .Download(loadState.error.localizedMessage ?: "Unreachable Error"))
                }
                if(loadStateMediator?.refresh is LoadState.NotLoading){
                    banner.submitState(
                        ConnectionState
                            .Connected
                            .Download("Data Updated"))
                }
            }
        }


    }


    private val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (position == adapter.itemCount && footerAdapter.itemCount > 0) {
                // если крайняя позичия и отображается футер
                quantity
            } else {
                1
            }
        }
    }


    private val navigationListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.nav_top_rated -> {
                viewModel.switchTypeList(TOP_VIEW_LIST)
                setToolbarTitle(TOP_VIEW_LIST)
                true
            }
            R.id.nav_now_playing -> {
                viewModel.switchTypeList(NOW_PLAYING_VIEW_LIST)
                setToolbarTitle(NOW_PLAYING_VIEW_LIST)
                true
            }
            R.id.nav_favorite -> {
                viewModel.switchTypeList(SEARCH_VIEW_LIST)
                setToolbarTitle(SEARCH_VIEW_LIST)
                true
            }
            R.id.nav_popular -> {
                viewModel.switchTypeList(POPULAR_VIEW_LIST)

                setToolbarTitle(POPULAR_VIEW_LIST)
                true
            }
            R.id.nav_upcoming -> {
                viewModel.switchTypeList(UPCOMING_VIEW_LIST)
                setToolbarTitle(UPCOMING_VIEW_LIST)
                true
            }
            else -> false
        }
    }

    override fun onItemClick(movieID: Int, view: View) {
        bottomNavView.hide()
        val fragmentManager = parentFragmentManager.beginTransaction()
        val fragment = FragmentMoviesDetails.getInstance(
            application = application,
            movieID = movieID,
            MOVIE_LIST_TAG
        )
        fragmentManager.apply {
            addSharedElement(view,view.resources.getString(R.string.details_transition))
            replace(
                R.id.activity_main_frame,
                fragment,
                MOVIE_DETAILS_TAG.toString()
            )
            addToBackStack(MOVIE_LIST_TAG.toString())
        }.commit()
    }

    private fun BottomNavigationView.hide() {
        ObjectAnimator.ofFloat(bottomNavView, View.TRANSLATION_Y, 200f).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavView.visibility = GONE
                }
            })
            interpolator = AccelerateDecelerateInterpolator()
            duration = 100L
            repeatCount = 1
            repeatMode = RESTART

        }.start()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(KEY_TOOLBAR_TITLE, listType)
    }


    private val prefsChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            key?.let {
                if (it == LANGUAGE_KEY.key || it == REGION_KEY.key) {
                    listType?.let { it1 -> viewModel.forceSwitchTypeList(it1) } // Обновление списка на снове новых настроек
                }
            }
        }

    override fun onDestroyView() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(prefsChangeListener)
        super.onDestroyView()
    }

}


