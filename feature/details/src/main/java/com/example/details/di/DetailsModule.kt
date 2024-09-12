package com.example.details.di

import com.example.details.data.DetailsRepositoryImpl
import com.example.details.domain.DetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DetailsModule {

    @Binds
    fun bindDetailsRepository(repository: DetailsRepositoryImpl): DetailsRepository
}
