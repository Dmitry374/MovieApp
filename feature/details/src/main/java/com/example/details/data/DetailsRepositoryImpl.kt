package com.example.details.data

import com.example.common.domain.NetworkResult
import com.example.details.domain.DetailsRepository
import com.example.model.domain.MovieDetail
import com.example.network.data.MovieApi
import com.example.network.data.extensions.map
import com.example.network.data.model.detail.MovieDetailResponse
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val detailResponseMapper: MovieDetailResponse.MovieDetailResponseMapper
) : DetailsRepository {

    override suspend fun getMovieDetail(titleId: Long): NetworkResult<MovieDetail> {
        return movieApi.getMovieDetails(titleId).map(detailResponseMapper)
    }
}
