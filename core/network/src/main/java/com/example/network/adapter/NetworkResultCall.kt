package com.example.network.adapter

import com.example.common.domain.NetworkResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResultCall<T>(
    private val proxy: Call<T>,
    private val resultTypeClass: Class<*>
) : Call<NetworkResult<T>> {

    override fun execute(): Response<NetworkResult<T>> {
        return try {
            val result = proxy.execute().mapToNetworkResult(resultTypeClass)
            Response.success(result)
        } catch (throwable: Throwable) {
            val errorResult = NetworkResult.Error(throwable)
            Response.success(errorResult)
        }
    }

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        val wrappingCallback = object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = response.mapToNetworkResult(resultTypeClass)
                callback.onResponse(this@NetworkResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                val errorResult = NetworkResult.Error(throwable)
                callback.onResponse(this@NetworkResultCall, Response.success(errorResult))
            }
        }
        proxy.enqueue(wrappingCallback)
    }

    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(proxy.clone(), resultTypeClass)

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun cancel() = proxy.cancel()

    @Suppress("UNCHECKED_CAST")
    private fun <T> Response<T>.mapToNetworkResult(paramType: Class<*>): NetworkResult<T> {
        return if (isSuccessful) {
            val body = body()

            when {
                paramType == Unit::class.java -> NetworkResult.Success(Unit as T)
                body != null -> NetworkResult.Success(body)
                else -> NetworkResult.Error(Throwable("Response body is empty"))
            }
        } else {
            NetworkResult.Error(Throwable(message()))
        }
    }
}
