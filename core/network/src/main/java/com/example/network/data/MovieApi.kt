package com.example.network.data

import com.example.common.domain.NetworkResult
import com.example.network.data.model.detail.MovieDetailResponse
import com.example.network.data.model.release.ReleasesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("v1/releases")
    suspend fun getReleases(): NetworkResult<ReleasesResponse>

    @GET("v1/title/{title_id}/details")
    suspend fun getMovieDetails(@Path("title_id") titleId: Long): NetworkResult<MovieDetailResponse>
}
