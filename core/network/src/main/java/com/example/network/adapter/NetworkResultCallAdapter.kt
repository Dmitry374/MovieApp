package com.example.network.adapter

import com.example.common.domain.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResultCallAdapter(
    private val resultType: Type,
    private val resultTypeClass: Class<*>
) : CallAdapter<Type, Call<NetworkResult<Type>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<NetworkResult<Type>> {
        return NetworkResultCall(
            proxy = call,
            resultTypeClass = resultTypeClass
        )
    }
}
