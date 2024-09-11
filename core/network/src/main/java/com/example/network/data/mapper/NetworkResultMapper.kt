package com.example.network.data.mapper

abstract class NetworkResultMapper<T, R> {
    abstract fun map(raw: T): R
}
