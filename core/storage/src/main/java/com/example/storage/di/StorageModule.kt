package com.example.storage.di

import android.content.Context
import androidx.room.Room
import com.example.storage.data.db.MoviesDao
import com.example.storage.data.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object StorageModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(
        @ApplicationContext context: Context
    ): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideMoviesDao(database: MoviesDatabase): MoviesDao {
        return database.moviesDao()
    }

    private const val DATABASE_NAME = "movies_database"
}
