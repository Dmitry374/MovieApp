package com.example.movies.domain

import com.example.common.domain.NetworkResult
import com.example.network.data.model.ReleasesResponse

interface MoviesRepository {
    suspend fun getMovies(): NetworkResult<ReleasesResponse>
}
