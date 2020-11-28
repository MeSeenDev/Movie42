package ru.meseen.dev.androidacademy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.meseen.dev.androidacademy.Helper.MOVIE_LIST
import ru.meseen.dev.androidacademy.data.CastData
import ru.meseen.dev.androidacademy.data.MovieData
import ru.meseen.dev.androidacademy.fragments.FragmentMoviesList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.add(R.id.activity_main_frame,
            FragmentMoviesList.getInstance(application, getTempData()),
            MOVIE_LIST.toString()
        )
        fragmentManager.commit()
    }


    private fun getTempData(): ArrayList<MovieData> {
        val list = ArrayList<MovieData>()

        list.add(
            MovieData(
                pg = 13,
                labelText = resources.getString(R.string.label_text),
                keywordsText = resources.getString(R.string.keywordsText),
                reviewsStars = 4,
                movieLength = 137,
                drawable = R.drawable.main_movie,
                reviewsText = resources.getString(R.string.reviewsText),
                descriptionText = resources.getString(R.string.descriptionText),
                cast = listOf(
                    CastData(name = "Robert Downey Jr.", drawable = R.drawable.movie),
                    CastData(name = "Chris Evans", drawable = R.drawable.movie_1),
                    CastData(name = "Mark Ruffalo", drawable = R.drawable.movie_2),
                    CastData(name = "Chris Hemsworth", drawable = R.drawable.movie_3),
                    CastData(name = "Robert Downey Jr.", drawable = R.drawable.movie),
                    CastData(name = "Chris Evans", drawable = R.drawable.movie_1),
                    CastData(name = "Mark Ruffalo", drawable = R.drawable.movie_2),
                    CastData(name = "Chris Hemsworth", drawable = R.drawable.movie_3)
                )
            )
        )
        list.add(
            MovieData(
                pg = 16,
                labelText = resources.getString(R.string.label_text0),
                keywordsText = resources.getString(R.string.keywordsText0),
                reviewsStars = 5,
                movieLength = 97,
                drawable = R.drawable.main_movie0,
                reviewsText = resources.getString(R.string.reviewsText0),
                descriptionText = resources.getString(R.string.descriptionText0)
            )
        )
        list.add(
            MovieData(
                pg = 13,
                labelText = resources.getString(R.string.label_text1),
                keywordsText = resources.getString(R.string.keywordsText1),
                reviewsStars = 4,
                movieLength = 102,
                drawable = R.drawable.main_movie1,
                reviewsText = resources.getString(R.string.reviewsText1),
                descriptionText = resources.getString(R.string.descriptionText1)
            )
        )
        list.add(
            MovieData(
                pg = 13,
                labelText = resources.getString(R.string.label_text2),
                keywordsText = resources.getString(R.string.keywordsText2),
                reviewsStars = 5,
                movieLength = 120,
                drawable = R.drawable.main_movie2,
                reviewsText = resources.getString(R.string.reviewsText2),
                descriptionText = resources.getString(R.string.descriptionText2)
            )
        )
        return list
    }


}