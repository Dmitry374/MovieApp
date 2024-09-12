package com.example.details.data

import com.example.common.extensions.getResult
import com.example.details.data.mapper.FavoriteMoviesEntityMapper
import com.example.details.data.mapper.MovieDetailMapper
import com.example.details.domain.DetailsRepository
import com.example.model.domain.MovieDetail
import com.example.network.data.MovieApi
import com.example.storage.data.db.MoviesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val moviesDao: MoviesDao,
    private val movieDetailMapper: MovieDetailMapper,
    private val favoriteMoviesEntityMapper: FavoriteMoviesEntityMapper
) : DetailsRepository {

    override fun listenMovieDetails(titleId: Long): Flow<MovieDetail> {
        return combine(
            flow {
                emit(
                    movieApi.getMovieDetails(titleId).getResult()
                )
            },
            moviesDao.listenIsFavoriteMovie(titleId),
            movieDetailMapper::map
        )
    }

    override suspend fun addFavorite(movieDetail: MovieDetail) {
        val entity = favoriteMoviesEntityMapper.map(movieDetail)
        moviesDao.insertFavoriteMovie(entity)
    }

    override suspend fun deleteFavorite(titleId: Long) {
        moviesDao.deleteFavoriteById(titleId)
    }
}
