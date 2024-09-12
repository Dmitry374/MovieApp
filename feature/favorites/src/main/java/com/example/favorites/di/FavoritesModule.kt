package com.example.favorites.di

import com.example.favorites.data.FavoritesRepositoryImpl
import com.example.favorites.domain.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesModule {

    @Binds
    fun bindsFavoritesRepository(repository: FavoritesRepositoryImpl): FavoritesRepository
}
