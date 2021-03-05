package ru.meseen.dev.androidacademy.data.repositories.impl

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
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
import ru.meseen.dev.androidacademy.data.repositories.AllMoviesListsRepository
import ru.meseen.dev.androidacademy.data.repositories.MovieItemRepository
import ru.meseen.dev.androidacademy.data.repositories.MovieListRepository
import ru.meseen.dev.androidacademy.data.repositories.SearchRepository
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient
import ru.meseen.dev.androidacademy.support.PreferencesKeys


@ExperimentalSerializationApi
@ExperimentalPagingApi

class Repository(val application: Application, val dataBase: RoomDataBase) :
    MovieListRepository, MovieItemRepository, SearchRepository, AllMoviesListsRepository {


    private val mainScope = (application as App).applicationScope


    @ExperimentalSerializationApi
    private val service = RetrofitClient.movieService

    init {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(application)
        val languageQuery = sharedPrefs.getString(
            PreferencesKeys.LANGUAGE_KEY.toString(),
            PreferencesKeys.LANGUAGE_KEY.defaultKey) ?: PreferencesKeys.LANGUAGE_KEY.defaultKey

        mainScope.launch(Dispatchers.IO) {
            val genresItems = service.getGenresList(languageQuery)
            val genresEntity =
                genresItems.genres.map { GenresEntity(genresItems = it, language = languageQuery) }
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
        val genresItem =
            movieResponse.genres.map { GenresEntity(it.genresId, it.genresName, language) }
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

    override suspend fun clearSearchBDQuery(listType: String) =
        dataBase.movieDao().clearSearchBDQuery(listType)

    override fun getAllMovies(): List<MovieDataEntity> = dataBase.movieDao().getAllMoviesListsRepository()






}