package com.example.topanime.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topanime.di.AnimeCat
import com.example.topanime.di.AnimeId
import com.example.topanime.domain.Anime
import com.example.topanime.usecases.FindAnimeUseCase
import com.example.topanime.usecases.SwitchAnimeFavUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor (@AnimeId private val animeId : Long,
                                                @AnimeCat private val animeCat : String,
                                                private val findAnimeUseCase: FindAnimeUseCase,
                                                private val switchAnimeFavUseCase: SwitchAnimeFavUseCase
)
    : ViewModel(){

    data class UiState(
        val animeDetail: Anime? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main){
            findAnimeUseCase(animeId, animeCat)?.collect {
                _state.value = UiState(it)
            }
        }
    }

    fun onFavClicked() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value.animeDetail?.let { switchAnimeFavUseCase(it) }
        }
    }
}
