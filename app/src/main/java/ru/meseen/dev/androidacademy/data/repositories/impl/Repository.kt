package ru.meseen.dev.androidacademy.data.repositories.impl

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import ru.meseen.dev.androidacademy.data.MovieRemoteMediator
import ru.meseen.dev.androidacademy.data.base.App
import ru.meseen.dev.androidacademy.data.base.RoomDataBase
import ru.meseen.dev.androidacademy.data.base.entity.GenresEntity
import ru.meseen.dev.androidacademy.data.base.entity.relations.MovieItemEntity
import ru.meseen.dev.androidacademy.data.base.query.CreditsQuery
import ru.meseen.dev.androidacademy.data.base.query.MovieListableQuery
import ru.meseen.dev.androidacademy.data.repositories.MovieItemRepository
import ru.meseen.dev.androidacademy.data.repositories.MovieListRepository
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient

@ExperimentalSerializationApi
class Repository(application: Application, private val dataBase: RoomDataBase) :
    MovieListRepository,MovieItemRepository {

    private val mainScope =(application as App).applicationScope

    private val movieDao = dataBase.movieDao()

    @ExperimentalSerializationApi
    private val service = RetrofitClient.movieService


    init{
        mainScope.launch{
            val genresItems = service.getGenresList("ru-RU")
            val genresEntity = genresItems.genres.map { GenresEntity(genresItems = it,language = "ru-RU") }
            dataBase.movieDao().insertAll(*genresEntity.toTypedArray())
        }
    }



    @ExperimentalSerializationApi
    @OptIn(ExperimentalPagingApi::class)
    override fun loadMoviesList(query: MovieListableQuery, pageSize: Int) = Pager(
        config = PagingConfig(pageSize = pageSize),
        remoteMediator = MovieRemoteMediator(dataBase = dataBase, service = service, query = query)
    ) {
        dataBase.movieDao().getMovieList(query.getMoviePath())
    }.flow



    enum class ListType(val selection: String) {
        TOP_VIEW_LIST("top_rated"), NOW_PLAYING_VIEW__LIST("now_playing")
        , POPULAR_VIEW_LIST("popular"), UPCOMING_VIEW_LIST("upcoming")
    }

    override fun loadMovieItem(
        query: CreditsQuery,
        pageSize: Int
    ): Flow<PagingData<MovieItemEntity>> {
       mainScope.launch {


        }
        TODO("Not yet implemented")
    }

}