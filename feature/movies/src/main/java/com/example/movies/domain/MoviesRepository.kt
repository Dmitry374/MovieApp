package com.example.movies.domain

import com.example.common.domain.NetworkResult
import com.example.model.domain.Movie
import com.example.model.domain.SearchItem

interface MoviesRepository {
    suspend fun getMovies(): NetworkResult<List<Movie>>
    suspend fun searchMovies(query: String): NetworkResult<List<SearchItem>>
}
