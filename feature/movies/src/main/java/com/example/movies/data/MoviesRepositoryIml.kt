package com.example.movies.data

import com.example.common.domain.NetworkResult
import com.example.movies.domain.MoviesRepository
import com.example.network.data.MovieApi
import com.example.network.data.model.ReleasesResponse
import javax.inject.Inject

class MoviesRepositoryIml @Inject constructor(
    private val movieApi: MovieApi
) : MoviesRepository {

    override suspend fun getMovies(): NetworkResult<ReleasesResponse> {
        return movieApi.getReleases()
    }
}
