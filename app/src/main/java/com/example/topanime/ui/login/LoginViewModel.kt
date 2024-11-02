package com.example.topanime.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topanime.ui.common.INVALID_EMAIL
import com.example.topanime.ui.common.INVALID_PASSWORD
import com.example.topanime.usecases.LoginUseCase
import com.example.topanime.usecases.ValidEmail
import com.example.topanime.usecases.ValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (private val loginUseCase: LoginUseCase,
                                          private val validEmail: ValidEmail,
                                          private val validPassword: ValidPassword) : ViewModel() {
    data class UiState(
        val loading : Boolean = false,
        val login : Boolean = false,
        val showError : String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    fun onLoginSelected(email: String, password: String) {
        _state.value = UiState(showError = null)

        if (isValidEmail(email) && isValidPassword(password)) {
            login(email, password)
        } else if(!isValidEmail(email)){
            _state.value = UiState(showError = INVALID_EMAIL)
        } else if(!isValidPassword(password)){
            _state.value = UiState(showError = INVALID_PASSWORD)
        }
    }

    private fun login(email : String, password : String) {
        viewModelScope.launch {
            _state.value = UiState(loading = true)

            val result = loginUseCase(email, password)

            if (result.success) {
                _state.value = UiState(login = true, loading = false)
            } else {
                _state.value = UiState(loading = false, showError = result.errorMsg)
            }
        }
    }

    private fun isValidPassword(password: String) = validPassword(password)

    private fun isValidEmail(email: String) = validEmail(email)
}
