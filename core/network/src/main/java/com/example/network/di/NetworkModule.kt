package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.adapter.NetworkResultCallAdapterFactory
import com.example.network.common.Constants
import com.example.network.data.MovieApi
import com.example.network.interceptor.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    internal fun provideJsonSerializer(): Json {
        return Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            namingStrategy = JsonNamingStrategy.SnakeCase
            isLenient = true
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(json: Json): Retrofit.Builder {
        return Retrofit.Builder()
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .baseUrl(Constants.BASE_URL)
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return retrofitBuilder.client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}
