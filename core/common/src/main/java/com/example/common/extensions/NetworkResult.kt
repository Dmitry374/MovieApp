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
    executable: suspend (throwable: Throwable) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Error) {
        executable(throwable)
    }
}

fun <T> NetworkResult<T>.getResult(): T {
    return when (this) {
        is NetworkResult.Success -> data
        is NetworkResult.Error -> throw throwable
    }
}
