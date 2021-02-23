package ru.meseen.dev.androidacademy.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.MovieClickListener
import ru.meseen.dev.androidacademy.adapters.PagingPageAdapter
import ru.meseen.dev.androidacademy.adapters.loaderStates.FooterLoadStateAdapter
import ru.meseen.dev.androidacademy.adapters.loaderStates.HeaderLoadStateAdapter
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieViewModel
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieViewModelFactory
import ru.meseen.dev.androidacademy.support.FragmentsTags.MOVIE_DETAILS_TAG
import ru.meseen.dev.androidacademy.support.FragmentsTags.MOVIE_LIST_TAG
import ru.meseen.dev.androidacademy.support.ListType
import ru.meseen.dev.androidacademy.support.ListType.*



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
    private lateinit var headerAdapter: HeaderLoadStateAdapter


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
        toolbar = view.findViewById(R.id.movies_list_toolbar)
        listType?.let { setToolbarTitle(it) }
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        return view
    }

    var quantity: Int = 2

    @ExperimentalSerializationApi
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleMain)
        quantity =
            view.resources.getInteger(R.integer.main_item_card_span) //todo возможно сделать глобальной
        adapter = PagingPageAdapter(listener = this)
        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        val bottomNavView: BottomNavigationView = view.findViewById(R.id.bottom_navigation)
        bottomNavView.setOnNavigationItemSelectedListener(navigationListener)
        recyclerView.setHasFixedSize(true)


        footerAdapter = FooterLoadStateAdapter(adapter) // ws 11
        headerAdapter = HeaderLoadStateAdapter(adapter) // ws 11
        recyclerView.adapter = adapter
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
                Log.d(TAG, "onViewCreated: $it")
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerView.scrollToPosition(0) } ///Обновляшка и сбрасовашка
        }

        // отслеживание состояний и вывод предупреждений, не особо удобно для обработки ошибок сети будут LoadStateAdapters
        lifecycleScope.launchWhenCreated {
            adapter.addLoadStateListener {
                val temp = it.append
                val snackbar = Snackbar.make(view, "$temp", LENGTH_LONG)
                if (temp is LoadState.Error) {
                    val text = temp.error.message
                    snackbar.setBackgroundTint(
                        ResourcesCompat.getColor(
                            view.resources,
                            R.color.colorAccent,
                            null
                        )
                    ).show()
                }
            }
        }

    }


    private val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (position == 0 && headerAdapter.itemCount > 0) {
                // если первая позичия и отображается хеадер
                quantity
            } else if (position == adapter.itemCount && footerAdapter.itemCount > 0) {
                // если крайняя позичия и отображается футер
                Log.d(TAG, "GridLayoutManager: ${adapter.itemCount}")
                quantity
            } else {
                1
            }
        }
    }


    private val navigationListener = BottomNavigationView.OnNavigationItemSelectedListener {


        when (it.itemId) {
            R.id.nav_top_rated -> {
                viewModel.switchTypeList(ListType.TOP_VIEW_LIST)
                setToolbarTitle(ListType.TOP_VIEW_LIST)
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

    override fun onItemClick(movieID: Int) {
        Toast.makeText(application, "movieID: $movieID", Toast.LENGTH_SHORT).show()
        val fragmentManager = parentFragmentManager.beginTransaction()
        fragmentManager.replace(
            R.id.activity_main_frame,
            FragmentMoviesDetails.getInstance(
                application = application,
                movieID = movieID,
                MOVIE_LIST_TAG
            ),
            MOVIE_DETAILS_TAG.toString()
        ).addToBackStack(MOVIE_LIST_TAG.toString())
            .setTransition(TRANSIT_FRAGMENT_OPEN)
            .commit()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(KEY_TOOLBAR_TITLE, listType)
    }

}


