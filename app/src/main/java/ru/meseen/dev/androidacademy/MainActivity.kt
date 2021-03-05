package ru.meseen.dev.androidacademy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.paging.ExperimentalPagingApi
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.data.base.query.impl.SearchViewQuery
import ru.meseen.dev.androidacademy.fragments.FragmentMoviesDetails
import ru.meseen.dev.androidacademy.fragments.FragmentMoviesList
import ru.meseen.dev.androidacademy.fragments.SearchFragment
import ru.meseen.dev.androidacademy.fragments.settings.ActivitySettings
import ru.meseen.dev.androidacademy.fragments.settings.MoviePrefsFragment
import ru.meseen.dev.androidacademy.support.FragmentsTags
import ru.meseen.dev.androidacademy.support.PreferencesKeys.*
import ru.meseen.dev.androidacademy.workers.notifications.Tag

@ExperimentalPagingApi
@ExperimentalSerializationApi
class MainActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView

    companion object {
        const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager = supportFragmentManager.beginTransaction()
        if (savedInstanceState == null) {
            fragmentManager.add(
                R.id.activity_main_frame,
                FragmentMoviesList.getInstance(application),
                FragmentsTags.MOVIE_LIST_TAG.toString()
            ).commit()
            intent?.let { checkActionIntent(it) }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.app_bar_search)
        searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(textListener)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.app_bar_settings) {
            startActivity(Intent(this@MainActivity ,ActivitySettings::class.java))
        }
        return false
    }

    private val textListener = object :
        SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            Log.d(TAG, "onQueryTextSubmit: $query")

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
            val languageForQuery =
                sharedPreferences.getString(LANGUAGE_KEY.key, LANGUAGE_KEY.defaultKey) ?: LANGUAGE_KEY.defaultKey
            val regionForQuery = sharedPreferences.getString(REGION_KEY.key, REGION_KEY.defaultKey) ?: REGION_KEY.defaultKey

            val redactorBottomSheet = SearchFragment.getInstance(
                application,
                SearchViewQuery(
                    path = "movie",
                    query = query ?: "Star",
                    language = languageForQuery,
                    region = regionForQuery
                )
            )
            invalidateOptionsMenu()
            redactorBottomSheet.show(
                supportFragmentManager,
                FragmentsTags.SEARCH_VIEW_TAG.toString()
            )

            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean { // после enter
            Log.d(TAG, "onQueryTextChange: $newText")


            return true
        }

    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkActionIntent(intent)

    }

    private fun checkActionIntent(intent: Intent?) {
        if (intent != null && intent.action == Intent.ACTION_VIEW) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
        Log.d(TAG, "handleIntent: ${intent.data}")

        val id = intent.data?.lastPathSegment?.split("-")?.get(0)?.toInt() ?: 0
        val fragment: BottomSheetDialogFragment =
            FragmentMoviesDetails.getInstance(
                application,
                id,
                FragmentsTags.INTENT_FROM_WM_VIEW_TAG
            ) as BottomSheetDialogFragment
        fragment.show(supportFragmentManager, FragmentsTags.INTENT_FROM_WM_VIEW_TAG.toString())
        NotificationManagerCompat.from(applicationContext)
            .cancel(Tag.NEW_MOVIE.toString(), Tag.NEW_MOVIE.uniqueIDN)

    }

}

