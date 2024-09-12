package com.example.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extensions.onError
import com.example.common.extensions.onSuccess
import com.example.movies.domain.MoviesRepository
import com.example.movies.presentation.mapper.MoviesUiStateMapper
import com.example.movies.presentation.model.MoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val moviesUiStateMapper: MoviesUiStateMapper
) : ViewModel() {

    private val _moviesStateFlow = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val moviesStateFlow = _moviesStateFlow.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            repository.getMovies()
                .onSuccess { movies ->
                    moviesUiStateMapper.map(movies)
                        .also(_moviesStateFlow::tryEmit)
                }
                .onError {
                    _moviesStateFlow.tryEmit(
                        MoviesUiState.Error(::getMovies)
                    )
                }
        }
    }
}
