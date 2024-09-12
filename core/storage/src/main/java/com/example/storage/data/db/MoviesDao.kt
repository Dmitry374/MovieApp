package com.example.storage.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storage.data.dbmodels.FavoriteMoviesDb
import com.example.storage.data.entities.FavoriteMoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMoviesEntity)

    @Query("SELECT * FROM favoritemovies")
    fun listenFavoriteMovies(): Flow<List<FavoriteMoviesDb>>

    @Query("SELECT EXISTS(SELECT id FROM favoritemovies WHERE id = :id)")
    suspend fun isFavoriteMovie(id: Long): Boolean

    @Query("DELETE FROM favoritemovies WHERE id = :id")
    suspend fun deleteFavoriteById(id: Long)
}
