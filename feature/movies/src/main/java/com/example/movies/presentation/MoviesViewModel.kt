package com.example.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extensions.onError
import com.example.common.extensions.onSuccess
import com.example.movies.domain.MoviesRepository
import com.example.movies.presentation.mapper.MoviesUiItemMapper
import com.example.movies.presentation.mapper.SearchUiItemMapper
import com.example.movies.presentation.model.MoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val moviesUiItemMapper: MoviesUiItemMapper,
    private val searchUiItemMapper: SearchUiItemMapper
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _moviesStateFlow = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val moviesStateFlow = _moviesStateFlow.asStateFlow()

    init {
        listenSearchQuery()
        getMovies()
    }

    fun onQueryChange(query: String) {
        _searchQuery.tryEmit(query)
    }

    fun onClearQuery() {
        _searchQuery.tryEmit("")
    }

    @OptIn(FlowPreview::class)
    private fun listenSearchQuery() {
        _searchQuery
            .debounce(500L)
            .onEach { query ->
                _moviesStateFlow.tryEmit(MoviesUiState.Loading)
                if (query.isEmpty()) {
                    getMovies()
                } else {
                    searchMovies(query)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            repository.searchMovies(query)
                .onSuccess { searchItems ->
                    searchUiItemMapper.map(searchItems)
                        .also(_moviesStateFlow::tryEmit)
                }
                .onError {
                    _moviesStateFlow.tryEmit(
                        MoviesUiState.Error(
                            onRetryAction = {
                                searchMovies(_searchQuery.value)
                            }
                        )
                    )
                }
        }
    }

    private fun getMovies() {
        viewModelScope.launch {
            repository.getMovies()
                .onSuccess { movies ->
                    moviesUiItemMapper.map(movies)
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
