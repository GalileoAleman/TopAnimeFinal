package com.example.topanime.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topanime.ui.common.INVALID_EMAIL
import com.example.topanime.ui.common.INVALID_PASSWORD
import com.example.topanime.ui.common.isValidEmail
import com.example.topanime.ui.common.isValidPassword
import com.example.topanime.usecases.SignupUseCase
import com.example.topanime.usecases.ValidEmail
import com.example.topanime.usecases.ValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor (private val signupUseCase: SignupUseCase,
                                           private val validEmail: ValidEmail,
                                           private val validPassword: ValidPassword
) : ViewModel() {
    data class UiState(
        val loading : Boolean = false,
        val signin : Boolean = false,
        val showError : String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    fun onSigninSelected(email: String, password: String) {
        _state.value = UiState(showError = null)

        if (isValidEmail(email) && isValidPassword(password)) {
            signin(email, password)
        } else if(!isValidEmail(email)){
            _state.value = UiState(showError = INVALID_EMAIL)
        } else if(!isValidPassword(password)){
            _state.value = UiState(showError = INVALID_PASSWORD)
        }
    }

    fun signin(email : String, password : String) {
        viewModelScope.launch {
            _state.value = UiState(loading = true)

            val result = signupUseCase(email, password)

            if (result.success) {
                _state.value = UiState(signin = true, loading = false)
            } else {
                _state.value = UiState(loading = false, showError = result.errorMsg)
            }
        }
    }

    private fun isValidPassword(password: String) = validPassword(password)

    private fun isValidEmail(email: String) = validEmail(email)
}
