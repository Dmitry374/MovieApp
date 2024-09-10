package com.example.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY_NAME, "")
            .build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }

    private companion object {
        const val API_KEY_NAME = "apiKey"
    }
}
