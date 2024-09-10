package com.example.common.domain

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()

    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val message: String?
    ) : NetworkResult<Nothing>()
}
