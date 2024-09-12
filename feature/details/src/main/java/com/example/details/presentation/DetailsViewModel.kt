package com.example.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extensions.onError
import com.example.common.extensions.onSuccess
import com.example.details.domain.DetailsRepository
import com.example.details.presentation.mapper.DetailsUiStateMapper
import com.example.details.presentation.model.DetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        getMovieDetail()
    }

    private fun getMovieDetail() {
        viewModelScope.launch {
            savedStateHandle.get<String>("titleId")?.toLong()?.let { titleId ->
                repository.getMovieDetail(titleId)
                    .onSuccess { detail ->
                        detailsUiStateMapper.map(detail)
                            .also(_detailsStateFlow::tryEmit)
                    }
                    .onError {
                        _detailsStateFlow.tryEmit(
                            DetailsUiState.Error(::getMovieDetail)
                        )
                    }
            }
        }
    }
}
