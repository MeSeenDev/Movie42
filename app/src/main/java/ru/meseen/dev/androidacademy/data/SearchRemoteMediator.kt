package ru.meseen.dev.androidacademy.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.meseen.dev.androidacademy.data.base.RoomDataBase
import ru.meseen.dev.androidacademy.data.base.entity.MovieDataEntity
import ru.meseen.dev.androidacademy.data.base.entity.PageKeyEntity
import ru.meseen.dev.androidacademy.data.base.query.MovieSearchQuery
import ru.meseen.dev.androidacademy.data.retrofit.RetrofitClient
import ru.meseen.dev.androidacademy.data.retrofit.service.MovieService
import java.io.IOException
import java.io.InvalidObjectException
import java.util.*

@ExperimentalPagingApi
class SearchRemoteMediator(
    private val query: MovieSearchQuery,
    private val service: MovieService,
    private val dataBase: RoomDataBase
) : RemoteMediator<Int, MovieDataEntity>() {

    companion object {
        const val TAG = "SearchRemoteMediator"
    }


    private val movieDao = dataBase.movieDao()
    private val pageKeyDao = dataBase.pageKeyDao()
    private lateinit var genres: Map<Int, String>

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDataEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {

                LoadType.REFRESH -> {
                    val remoteKeys =
                        getRemoteKeyClosestToCurrentPosition(state, query.getMoviePath())
                    remoteKeys?.nextPage?.minus(1) ?: RetrofitClient.START_PAGE
                }
                LoadType.PREPEND -> {
                    // LoadType - PREPEND, значит некоторые данные были ранее загружены,
                    // чтобы мы могли получить удаленные ключи
                    // Если удаленные ключи равны null, значит, руки из жопы получаем недопустимое состояние от туда и Исключение
                    val remoteKeys = getRemoteKeyForFirstItem(state, query.getMoviePath())
                        ?: throw InvalidObjectException("Remote key and the prevKey should not be null")

                    remoteKeys.prevPage
                        ?: return MediatorResult.Success(  // Если предыдущий ключ равен нулю, мы не можем запросить больше данных
                            endOfPaginationReached = true
                        )
                    remoteKeys.prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state, query.getMoviePath())
                    Log.d(TAG, "APPEND load: $remoteKeys")

                    if (remoteKeys?.nextPage == null) {
                        throw InvalidObjectException("Remote key should not be null for $loadType")
                    }
                    remoteKeys.nextPage
                }
            }


            Log.d(TAG, "load Query : $query, $page")

            val resultItem = service.searchMovies(
                searchType = query.getMoviePath(),
                language = query.getMoviesLanguage(),
                region = query.getMoviesRegion(),
                page = page,
                year = query.getMovieYear(),
                primaryReleaseYear = query.getMoviePrimaryReleaseYear(),
                include_adult = query.isMovieIncludeAdult(),
                query = query.getSearchQuery()
            )



            dataBase.withTransaction {
                genres = dataBase.movieDao()
                    .getListGenreEntity(query.getMoviesLanguage())
                    .map { it.genres_id to it.genresName.capitalize(Locale.ROOT) }.toMap()
            }

            Log.d(TAG, "load: $resultItem")
            val resultEntity = resultItem.results.map { movieItem ->
                MovieDataEntity(
                    movieItem,
                    query.getMoviePath(),
                    genres = movieItem.genreIds.filterNotNull()
                        .joinToString { genres[it] ?: "" }
                )
            }

            val endOfPaginationReached = resultEntity.isEmpty()

            dataBase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    pageKeyDao.deleteByListType(query.getMoviePath())
                    movieDao.deleteByListType(query.getMoviePath())
                }

                val prevPage = if (page == RetrofitClient.START_PAGE) null else page - 1
                val nextPage = if (endOfPaginationReached) null else page + 1

                val keys = resultEntity.map {
                    PageKeyEntity(
                        movieId = it.movieId,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        listType = query.getMoviePath()
                    )
                }
                pageKeyDao.insertAll(keys)
                movieDao.insertAll(*resultEntity.toTypedArray())
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (ioe: IOException) {
            Log.d(TAG, "load: ${ioe.localizedMessage}")
            MediatorResult.Error(ioe)
        } catch (httpE: HttpException) {
            Log.d(TAG, "load: ${httpE.localizedMessage}")
            MediatorResult.Error(httpE)
        }
    }


    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieDataEntity>,
        listType: String
    ): PageKeyEntity? {
        // Берем последнюю полученную страницу, содержащую элементы.
        // С этой последней страницы получаем последний элемент
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { resultEntity ->
                dataBase.pageKeyDao()
                    .remoteKeyById(
                        resultEntity.movieId,
                        listType
                    ) // получение последнего удаленного ключа или null
            }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieDataEntity>,
        listType: String
    ): PageKeyEntity? {
        // Берем первую полученную страницу, содержащую элементы.
        // С этой первой страницы получаем первый элемент
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { resultEntity ->
                dataBase.pageKeyDao()
                    .remoteKeyById(
                        resultEntity.movieId,
                        listType
                    ) // получение первого удаленного ключа или null
            }

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieDataEntity>, listType: String
    ): PageKeyEntity? {
        // Библиотека пагинации(3.0*) пытается загрузить данные после позиции привязки
        // Получаем элемент, ближайший к позиции привязки
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { movieId ->
                dataBase.pageKeyDao().remoteKeyById(movieId, listType)
            }
        }
    }


}