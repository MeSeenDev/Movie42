package ru.meseen.dev.androidacademy.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.from
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.adapters.MovieClickListener
import ru.meseen.dev.androidacademy.adapters.PagingPageAdapter
import ru.meseen.dev.androidacademy.data.base.query.SearchQuery
import ru.meseen.dev.androidacademy.data.base.query.impl.SearchViewQuery
import ru.meseen.dev.androidacademy.fragments.viewmodel.MovieViewModelFactory
import ru.meseen.dev.androidacademy.fragments.viewmodel.SearchViewModel
import ru.meseen.dev.androidacademy.fragments.viewmodel.SearchViewModel.Companion.KEY_SEARCH_MOVIES
import ru.meseen.dev.androidacademy.support.FragmentsTags
import ru.meseen.dev.androidacademy.support.ListType.*
import ru.meseen.dev.androidacademy.support.PreferencesKeys

@ExperimentalPagingApi
@ExperimentalSerializationApi
class SearchFragment : BottomSheetDialogFragment(), MovieClickListener {

    private lateinit var searchQuery: SearchQuery
    private lateinit var application: Application

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private val viewModel by viewModels<SearchViewModel> {
        val bundle = Bundle()
        arguments?.let { argsBundle ->
            argsBundle.getSerializable(KEY_SEARCH_MOVIES)?.let { queue ->
                bundle.putSerializable(
                    KEY_SEARCH_MOVIES,
                    queue as SearchViewQuery
                )
            }
        }
        MovieViewModelFactory(this, application, bundle)
    }

    companion object {
        const val TAG = "SearchFragment"
        fun getInstance(
            application: Application,
            query: SearchViewQuery
        ): BottomSheetDialogFragment {
            val instance = SearchFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_SEARCH_MOVIES, query)
            Log.d(SearchViewModel.TAG, ": getInstance $query")
            instance.arguments = bundle
            instance.application = application
            return instance
        }
    }

    @ExperimentalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchQuery = arguments?.getSerializable(KEY_SEARCH_MOVIES) as SearchQuery
        @Suppress("DEPRECATION")
        retainInstance = true
        Log.d(TAG, "onCreate: Fragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        configBottomSheet(view)
        Log.d(SearchViewModel.TAG, ": onCreateView ")

        return view
    }

    private fun configBottomSheet(view: View) {
        val windowHeight: Int =
            view.context.resources.displayMetrics.heightPixels
        val parentSheet: CoordinatorLayout = view.findViewById(R.id.bottomSheet)
        bottomSheetBehavior = from(parentSheet)

        bottomSheetBehavior.isHideable = true
        val layoutParams = parentSheet.layoutParams
        layoutParams.height = windowHeight
        parentSheet.layoutParams = layoutParams
        bottomSheetBehavior.state = STATE_EXPANDED
    }


    @ExperimentalStdlibApi
    @ExperimentalSerializationApi
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchConfig(view.findViewById(R.id.search_view))

        val adapter = PagingPageAdapter(this)
        val gridLayoutManager: RecyclerView.LayoutManager
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recycle)
        val swipeRefreshLayout =
            view.findViewById<SwipeRefreshLayout>(R.id.swipeSearchRefreshLayout)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        // Временное решение

        val quantity = view.resources.getInteger(R.integer.main_item_card_span)
        gridLayoutManager = GridLayoutManager(application.baseContext, quantity)
        gridLayoutManager.isUsingSpansToEstimateScrollbarDimensions = true
        recyclerView.layoutManager = gridLayoutManager

        swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
                Log.d(SearchViewModel.TAG, ": swipeRefreshLayout.isRefreshing ")
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.searchMovies.collectLatest {
                adapter.submitData(it)
                Log.d(TAG, "launchWhenCreated: searchMovies append Data")
            }
        }
        lifecycleScope.launchWhenCreated {
            Log.d(TAG, "launchWhenCreated: adapter.loadStateFlow")
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerView.scrollToPosition(0) } ///Обновляшка и сбрасовашка
        }

    }

    @ExperimentalStdlibApi
    @ExperimentalSerializationApi
    @ExperimentalPagingApi
    private fun searchConfig(searchView: SearchView) {
        searchView.setQuery(searchQuery.getSearchQuery(), true)
        searchView.queryHint = searchQuery.getSearchQuery()

        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(onQueryListener)
    }

    @ExperimentalStdlibApi
    @ExperimentalSerializationApi
    private val onQueryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
            val languageForQuery =
                sharedPreferences.getString(PreferencesKeys.LANGUAGE_KEY.key, PreferencesKeys.LANGUAGE_KEY.defaultKey) ?: PreferencesKeys.LANGUAGE_KEY.defaultKey
            val regionForQuery = sharedPreferences.getString(PreferencesKeys.REGION_KEY.key, PreferencesKeys.REGION_KEY.defaultKey) ?: PreferencesKeys.REGION_KEY.defaultKey

            viewModel.reQuery(
                SearchViewQuery(
                    query = query as String, language = languageForQuery, region = "${languageForQuery.lowercase()}-$regionForQuery",
                    path = SEARCH_VIEW_LIST.selection
                )
            )
            Log.d(TAG, "onStateChanged: reQuery $query")
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            //TODO отложенный ввод
            return true

        }

    }


    override fun onItemClick(movieID: Int,view: View) {
        Toast.makeText(application, "movieID: $movieID", Toast.LENGTH_SHORT).show()
        val frag: BottomSheetDialogFragment = FragmentMoviesDetails.getInstance(
            application = application,
            movieID = movieID,
            FragmentsTags.SEARCH_VIEW_TAG
        ) as BottomSheetDialogFragment
        frag.show(childFragmentManager, FragmentsTags.MOVIE_DETAILS_TAG.toString())

    }

    override fun onDestroyView() {
        viewModel.clearSearchResults()
        super.onDestroyView()
    }


}