package com.example.movies.data

import com.example.common.domain.NetworkResult
import com.example.model.domain.Movie
import com.example.movies.domain.MoviesRepository
import com.example.network.data.MovieApi
import com.example.network.data.extensions.map
import com.example.network.data.model.ReleasesResponse
import javax.inject.Inject

class MoviesRepositoryIml @Inject constructor(
    private val movieApi: MovieApi,
    private val mapper: ReleasesResponse.ReleasesResponseMapper
) : MoviesRepository {

    override suspend fun getMovies(): NetworkResult<List<Movie>> {
        return movieApi.getReleases().map(mapper)
    }
}
