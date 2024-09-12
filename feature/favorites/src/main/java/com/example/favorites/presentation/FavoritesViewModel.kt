package com.example.favorites.presentation

import androidx.lifecycle.ViewModel
import com.example.favorites.domain.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

}
