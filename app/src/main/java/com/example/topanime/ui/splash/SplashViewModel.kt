package com.example.topanime.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topanime.usecases.CurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor (private val currentUserUseCase: CurrentUserUseCase) : ViewModel() {

    data class UiState(
        val userLoged : Boolean? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init{
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState(userLoged = currentUserUseCase())
        }
    }
}
