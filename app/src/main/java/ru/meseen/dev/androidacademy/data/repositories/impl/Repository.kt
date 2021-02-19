package ru.meseen.dev.androidacademy.data.repositories.impl

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.data.MovieRemoteMediator
import ru.meseen.dev.androidacademy.data.SearchRemoteMediator
import ru.meseen.dev.androidacademy.data.base.App
import ru.meseen.dev.androidacademy.data.base.RoomDataBase
import ru.meseen.dev.androidacademy.data.base.entity.CastEntity
import ru.meseen.dev.androidacademy.data.base.entity.GenresEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieAdditionalDataEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity
import ru.meseen.dev.androidacademy.data.base.query.MovieByIdQuery
import ru.meseen.dev.androidacademy.data.base.query.MovieListableQuery
import ru.meseen.dev.androidacademy.data.base.query.MovieSearchQuery
import ru.meseen.dev.androidacademy.data.repositories.MovieItemRepository
import ru.meseen.dev.androidacademy.data.repositories.MovieListRepository
import ru.meseen.dev.androidacademy.data.repositories.SearchRepository
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient


@ExperimentalSerializationApi
class Repository(application: Application, val dataBase: RoomDataBase) :
    MovieListRepository, MovieItemRepository, SearchRepository {

    private val mainScope = (application as App).applicationScope


    @ExperimentalSerializationApi
    private val service = RetrofitClient.movieService

    init {
        mainScope.launch(Dispatchers.IO) {
            val genresItems = service.getGenresList("ru-RU")
            val genresEntity =
                genresItems.genres.map { GenresEntity(genresItems = it, language = "ru-RU") }
            dataBase.movieDao().insertAll(*genresEntity.toTypedArray())
        }
    }





    private val movieID: MutableLiveData<MovieItemEntity> = MutableLiveData()

    @ExperimentalSerializationApi
    override fun loadMovieItem(
        query: MovieByIdQuery
    ): LiveData<MovieItemEntity> {
        mainScope.launch(Dispatchers.IO) {
            movieID.postValue(
                getMovieItemById(
                    query.getMovieID(),
                    query.getMoviesLanguage(),
                    query.getAppendToResponse()
                )
            )
        }
        return movieID
    }

    @ExperimentalSerializationApi
    private suspend fun getMovieItemById(
        movie_id: Int,
        language: String,
        appendToResponse: String
    ): MovieItemEntity {
        val movieResponse = service.getMovieById(movie_id.toString(), language, appendToResponse)
        val movieItem =
            MovieAdditionalDataEntity(movieResponse, movieResponse.genres.joinToString())
        val casts =
            service.getMovieCastById(movie_id.toString(), language).cast.map {
                CastEntity(
                    it,
                    language
                )
            }
        val genresItem = service.getGenresList(language).genres.map { GenresEntity(it, language) }
        return MovieItemEntity(
            movieAddData = movieItem,
            castList = casts,
            genresEntity = genresItem
        )
    }
    override fun clearMovieItem() {
        movieID.value = null
    }




    @ExperimentalPagingApi
    @ExperimentalSerializationApi
    override fun loadMoviesList(query: MovieListableQuery, pageSize: Int) = Pager(
        config = PagingConfig(pageSize = pageSize),
        remoteMediator = MovieRemoteMediator(dataBase = dataBase, service = service, query = query)
    ) {
        dataBase.movieDao().getMovieList(query.getMoviePath())
    }.flow


    @ExperimentalSerializationApi
    @ExperimentalPagingApi
    override fun search(
        query: MovieSearchQuery,
        pageSize: Int
    ): Flow<PagingData<MovieDataEntity>> = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = SearchRemoteMediator(dataBase = dataBase, service = service, query = query)
    ) {
        dataBase.movieDao().getMovieList(query.getMoviePath())
    }.flow

    override suspend fun clearSearchBDQuery(listType: String) = dataBase.movieDao().clearSearchBDQuery(listType)



    enum class ListType(val selection: String) {


        TOP_VIEW_LIST("top_rated"), NOW_PLAYING_VIEW__LIST("now_playing")
        , POPULAR_VIEW_LIST("popular"), UPCOMING_VIEW_LIST("upcoming"),SEARCH_VIEW_LIST("movie");

        fun getLocalizedName(resources: Resources): String {
            return when (selection) {
                "top_rated" -> resources.getString(R.string.top_rated)
                "now_playing" -> resources.getString(R.string.now_playing)
                "popular" -> resources.getString(R.string.popular)
                "upcoming" -> resources.getString(R.string.upcoming)
                "movie" ->resources.getString(R.string.movie_search)
                else -> {
                    throw IllegalArgumentException("No such Type Exists")
                }
            }
        }

    }
}