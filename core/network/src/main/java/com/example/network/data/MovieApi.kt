package com.example.network.data

import com.example.common.domain.NetworkResult
import com.example.network.data.model.detail.MovieDetailResponse
import com.example.network.data.model.release.ReleasesResponse
import com.example.network.data.model.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("v1/releases")
    suspend fun getReleases(): NetworkResult<ReleasesResponse>

    @GET("v1/title/{title_id}/details")
    suspend fun getMovieDetails(@Path("title_id") titleId: Long): NetworkResult<MovieDetailResponse>

    @GET("v1/autocomplete-search?search_type=2")
    suspend fun searchMovies(@Query("search_value") query: String): NetworkResult<SearchResponse>
}
