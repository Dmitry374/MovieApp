package com.example.movies.presentation

import androidx.lifecycle.ViewModel
import com.example.movies.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

}
