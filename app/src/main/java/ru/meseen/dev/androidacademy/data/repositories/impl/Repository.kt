package ru.meseen.dev.androidacademy.data.repositories.impl

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.data.MovieRemoteMediator
import ru.meseen.dev.androidacademy.data.SearchRemoteMediator
import ru.meseen.dev.androidacademy.data.base.App
import ru.meseen.dev.androidacademy.data.base.RoomDataBase
import ru.meseen.dev.androidacademy.data.base.entity.CastEntity
import ru.meseen.dev.androidacademy.data.base.entity.GenresEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieAdditionalDataEntity
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity
import ru.meseen.dev.androidacademy.data.base.entity.relations.CastList
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieDetailViewItems
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
import java.util.*


@ExperimentalSerializationApi
@ExperimentalPagingApi

class Repository(val application: Application, val dataBase: RoomDataBase) :
    MovieListRepository, MovieItemRepository, SearchRepository, AllMoviesListsRepository {


    private val mainScope = (application as App).applicationScope

    private val sharedPrefs: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(application).apply {
            registerOnSharedPreferenceChangeListener(prefsListener)
        }


    @ExperimentalSerializationApi
    private val service = RetrofitClient.movieService

    init {
        updateGenres()
    }

    private fun updateGenres() {
        val languageQuery = sharedPrefs.getString(
            PreferencesKeys.LANGUAGE_KEY.key,
            PreferencesKeys.LANGUAGE_KEY.defaultKey
        ) ?: PreferencesKeys.LANGUAGE_KEY.defaultKey

        mainScope.launch(Dispatchers.IO) {
            val genresItems = service.getGenresList(languageQuery)
            val genresEntity =
                genresItems.genres.map { GenresEntity(genresItems = it, language = languageQuery) }
            dataBase.movieDao().insertAll(*genresEntity.toTypedArray())
        }
    }



    @ExperimentalSerializationApi
    override fun loadMovieItem(
        query: MovieByIdQuery
    ): Flow<MovieDetailViewItems> = flow {
        val movieId: Int =query.getMovieID()
        val language: String = query.getMoviesLanguage()
        val appendToResponse: String = query.getAppendToResponse()


        val list = mutableListOf<MovieDetailViewItems>()

        val movieResponse = service.getMovieById(movieId.toString(), language, appendToResponse)
        val genresItem =
            movieResponse.genres.map { GenresEntity(it.genresId,
                it.genresName.capitalize(Locale.ROOT), language) }
        val movieItem =
            MovieAdditionalDataEntity(movieResponse, genresItem)

        emit(movieItem)

        list.add(movieItem)

        val casts = CastList(
            service.getMovieCastById(movieId.toString(), language).cast.map {
                CastEntity(
                    it,
                    language
                )
            })

        emit(casts)
    }.flowOn(Dispatchers.IO)





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

    override fun clearSearchBDQuery(listType: String) {
        mainScope.launch(Dispatchers.IO) {
            dataBase.movieDao().clearSearchBDQuery(listType)
        }
    }


    override suspend fun getAllMovies(): List<MovieDataEntity> =
        dataBase.movieDao().getAllMoviesListsRepository()


    private val prefsListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == PreferencesKeys.LANGUAGE_KEY.key) {
                updateGenres()
            }
        }


}