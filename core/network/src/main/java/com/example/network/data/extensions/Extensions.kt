package com.example.network.data.extensions

import com.example.common.domain.NetworkResult
import com.example.network.data.mapper.NetworkResultMapper

fun <T, R> NetworkResult<T>.map(
    mapper: NetworkResultMapper<T, R>
): NetworkResult<R> {
    return when (this) {
        is NetworkResult.Success -> {
            NetworkResult.Success(mapper.map(this.data))
        }

        is NetworkResult.Error -> this
    }
}
