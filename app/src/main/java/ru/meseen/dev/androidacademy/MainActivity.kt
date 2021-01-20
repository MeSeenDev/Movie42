package ru.meseen.dev.androidacademy

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import ru.meseen.dev.androidacademy.fragments.FragmentMoviesList
import ru.meseen.dev.androidacademy.support.FragmentsTags

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager.beginTransaction()
            fragmentManager.add(
                R.id.activity_main_frame,
                FragmentMoviesList.getInstance(application),
                FragmentsTags.MOVIE_LIST_TAG.toString()
            ).commit()
        }
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


}

