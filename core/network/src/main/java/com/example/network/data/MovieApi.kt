package com.example.network.data

import com.example.common.domain.NetworkResult
import com.example.network.data.model.ReleasesResponse
import retrofit2.http.GET

interface MovieApi {
    @GET("v1/releases")
    suspend fun getReleases(): NetworkResult<ReleasesResponse>
}
