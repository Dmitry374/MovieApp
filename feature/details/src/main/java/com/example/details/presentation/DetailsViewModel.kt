package com.example.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.details.domain.DetailsRepository
import com.example.details.presentation.mapper.DetailsUiStateMapper
import com.example.details.presentation.model.DetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository,
    private val savedStateHandle: SavedStateHandle,
    private val detailsUiStateMapper: DetailsUiStateMapper
) : ViewModel() {

    private val _detailsStateFlow = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val detailsStateFlow = _detailsStateFlow.asStateFlow()

    init {
        listenMovieDetails()
    }

    fun onFavoriteClick(uiDetails: DetailsUiState.Success) {
        viewModelScope.launch {
            if (uiDetails.isFavorite) {
                repository.deleteFavorite(uiDetails.id)
            } else {
                repository.addFavorite(detailsUiStateMapper.map(uiDetails))
            }
        }
    }

    private fun listenMovieDetails() {
        savedStateHandle.get<String>("titleId")?.toLong()?.let { titleId ->
            repository.listenMovieDetails(titleId)
                .onStart {
                    _detailsStateFlow.tryEmit(
                        DetailsUiState.Loading
                    )
                }
                .catch {
                    _detailsStateFlow.tryEmit(
                        DetailsUiState.Error(::listenMovieDetails)
                    )
                }
                .onEach { detail ->
                    detailsUiStateMapper.map(detail)
                        .also(_detailsStateFlow::tryEmit)
                }
                .launchIn(viewModelScope)
        }
    }
}
