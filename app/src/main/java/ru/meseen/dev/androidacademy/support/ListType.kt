package ru.meseen.dev.androidacademy.support

import android.content.res.Resources
import ru.meseen.dev.androidacademy.R

enum class ListType(val selection: String) {


    TOP_VIEW_LIST("top_rated"), NOW_PLAYING_VIEW_LIST("now_playing"), POPULAR_VIEW_LIST("popular"), UPCOMING_VIEW_LIST(
        "upcoming"
    ),
    SEARCH_VIEW_LIST("movie");

    fun getLocalizedName(resources: Resources): String {
        return when (selection) {
            "top_rated" -> resources.getString(R.string.top_rated)
            "now_playing" -> resources.getString(R.string.now_playing)
            "popular" -> resources.getString(R.string.popular)
            "upcoming" -> resources.getString(R.string.upcoming)
            "movie" -> resources.getString(R.string.movie_search)
            else -> {
                throw IllegalArgumentException("No such Type Exists")
            }
        }
    }

}