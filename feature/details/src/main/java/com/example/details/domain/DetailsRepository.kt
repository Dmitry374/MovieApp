package com.example.details.domain

import com.example.model.domain.MovieDetail
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun listenMovieDetails(titleId: Long): Flow<MovieDetail>
    suspend fun addFavorite(movieDetail: MovieDetail)
    suspend fun deleteFavorite(titleId: Long)
}
