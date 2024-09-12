package com.example.common.domain

sealed interface NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>

    data class Error(val throwable: Throwable) : NetworkResult<Nothing>
}
