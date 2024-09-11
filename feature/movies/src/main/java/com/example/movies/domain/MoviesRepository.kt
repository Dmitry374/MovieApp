package com.example.movies.domain

import com.example.common.domain.NetworkResult
import com.example.model.domain.Movie

interface MoviesRepository {
    suspend fun getMovies(): NetworkResult<List<Movie>>
}
