package com.example.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favorites.domain.FavoritesRepository
import com.example.favorites.presentation.mapper.FavoriteMoviesUiStateMapper
import com.example.favorites.presentation.model.FavoriteMoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository,
    private val favoriteMoviesUiStateMapper: FavoriteMoviesUiStateMapper
) : ViewModel() {

    private val _favoriteMoviesStateFlow =
        MutableStateFlow<FavoriteMoviesUiState>(FavoriteMoviesUiState.Loading)
    val favoriteMoviesStateFlow = _favoriteMoviesStateFlow.asStateFlow()

    init {
        listenFavoriteMovies()
    }

    private fun listenFavoriteMovies() {
        repository.listenFavoritesMovies()
            .onStart {
                _favoriteMoviesStateFlow.tryEmit(
                    FavoriteMoviesUiState.Loading
                )
            }
            .catch {
                _favoriteMoviesStateFlow.tryEmit(
                    FavoriteMoviesUiState.Error(::listenFavoriteMovies)
                )
            }
            .onEach { movies ->
                favoriteMoviesUiStateMapper.map(movies)
                    .also(_favoriteMoviesStateFlow::tryEmit)
            }
            .launchIn(viewModelScope)
    }
}
