package com.example.details.domain

import com.example.common.domain.NetworkResult
import com.example.model.domain.MovieDetail

interface DetailsRepository {
    suspend fun getMovieDetail(titleId: Long): NetworkResult<MovieDetail>
}
