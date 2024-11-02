package com.example.topanime.ui.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.topanime.usecases.GetUserUseCase
import com.example.topanime.usecases.LoadImageUseCase
import com.example.topanime.usecases.SaveImageUseCase
import com.example.topanime.usecases.SignoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val signoutUseCase: SignoutUseCase,
                                        private val saveImageUseCase: SaveImageUseCase,
                                        private val loadImageUseCase: LoadImageUseCase,
                                        private val getUserUseCase: GetUserUseCase)
    : ViewModel() {

    data class UiState(
        val signOut : Boolean = false,
        val image : Uri? = null,
        val userName : String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init {
        userInfo()
    }

    fun signOut(){
        signoutUseCase()
        _state.value = UiState(signOut = true)
    }

    fun saveImage(img: Uri) {
        saveImageUseCase(img.toString())
        loadImage()
    }


    private fun loadImage() {
        val img = loadImageUseCase()

        if(img.isNotEmpty())
            _state.value = UiState(image = Uri.parse(img))
    }

    private fun getUserName() {
        val user = getUserUseCase()

        if(user.isNotEmpty())
            _state.value = state.value.copy(userName = user)
    }

    private fun userInfo() {
        loadImage()
        getUserName()
    }
}
