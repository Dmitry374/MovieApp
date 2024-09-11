package com.example.movies.di

import com.example.movies.data.MoviesRepositoryIml
import com.example.movies.domain.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MoviesModule {

    @Binds
    fun bindMoviesRepository(repository: MoviesRepositoryIml): MoviesRepository
}
