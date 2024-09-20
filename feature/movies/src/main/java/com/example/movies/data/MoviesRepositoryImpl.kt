package com.example.movies.data

import com.example.common.domain.NetworkResult
import com.example.model.domain.Movie
import com.example.model.domain.SearchResultItem
import com.example.movies.domain.MoviesRepository
import com.example.network.data.MovieApi
import com.example.network.data.extensions.map
import com.example.network.data.model.release.ReleasesResponse
import com.example.network.data.model.search.SearchResponse
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val releasesResponseMapper: ReleasesResponse.ReleasesResponseMapper,
    private val searchResponseMapper: SearchResponse.SearchResponseMapper
) : MoviesRepository {

    override suspend fun getMovies(): NetworkResult<List<Movie>> {
        return movieApi.getReleases().map(releasesResponseMapper)
    }

    override suspend fun searchMovies(query: String): NetworkResult<List<SearchResultItem>> {
        return movieApi.searchMovies(query).map(searchResponseMapper)
    }
}
