package com.example.common.extensions

import com.example.common.domain.NetworkResult

suspend fun <T> NetworkResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Success<T>) {
        executable(data)
    }
}

suspend fun <T> NetworkResult<T>.onError(
    executable: suspend (isNetworkError: Boolean, code: Int?, message: String?) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Error) {
        executable(isNetworkError, errorCode, message)
    }
}
