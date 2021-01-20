package ru.meseen.dev.androidacademy

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.meseen.dev.androidacademy.data.Repository
import ru.meseen.dev.androidacademy.fragments.FragmentMoviesList
import ru.meseen.dev.androidacademy.support.FragmentsTags

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainToolBar = findViewById<Toolbar>(R.id.movies_list_toolbar)
        setSupportActionBar(mainToolBar)
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavView.setOnNavigationItemSelectedListener(navigationListener)

        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager.beginTransaction()
            fragmentManager.add(
                R.id.activity_main_frame,
                FragmentMoviesList.getInstance(application, Repository.ListType.TOP_VIEW_LIST),
                FragmentsTags.MOVIE_LIST_TAG.toString()
            ).commit()
        }
    }

    private val navigationListener = BottomNavigationView.OnNavigationItemSelectedListener {
        val fragmentManager = supportFragmentManager.beginTransaction()

        when (it.itemId) {
            R.id.nav_top_rated -> {
                fragmentManager.add(
                    FragmentMoviesList.getInstance(
                        application,
                        Repository.ListType.TOP_VIEW_LIST
                    ), Repository.ListType.TOP_VIEW_LIST.toString()
                ).commit()
                true
            }
            R.id.nav_now_playing -> {
                fragmentManager.add(
                    FragmentMoviesList.getInstance(
                        application,
                        Repository.ListType.NOW_PLAYING_VIEW__LIST
                    ), Repository.ListType.NOW_PLAYING_VIEW__LIST.toString()
                ).commit()
                true
            }
            R.id.nav_favorite -> {

                true
            }
            R.id.nav_popular -> {
                fragmentManager.add(
                    FragmentMoviesList.getInstance(
                        application,
                        Repository.ListType.POPULAR_VIEW_LIST
                    ), Repository.ListType.POPULAR_VIEW_LIST.toString()
                ).commit()
                true
            }
            R.id.nav_upcoming -> {
                fragmentManager.add(
                    FragmentMoviesList.getInstance(
                        application,
                        Repository.ListType.UPCOMING_VIEW_LIST
                    ), Repository.ListType.UPCOMING_VIEW_LIST.toString()
                ).commit()
                true
            }
            else -> false
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


}

