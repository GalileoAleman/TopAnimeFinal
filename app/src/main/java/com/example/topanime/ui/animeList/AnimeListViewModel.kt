package com.example.topanime.ui.animeList

import androidx.lifecycle.*
import com.example.topanime.di.IoDispatcher
import com.example.topanime.domain.AnimeCategories
import com.example.topanime.domain.AnimeListResult
import com.example.topanime.usecases.RequestSeasonNowUseCase
import com.example.topanime.usecases.RequestTopAnimeUseCase
import com.example.topanime.usecases.RequestTopMangaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor (private val requestSeasonNowUseCase: RequestSeasonNowUseCase,
                                              private val requestTopAnimeUseCase: RequestTopAnimeUseCase,
                                              private val requestTopMangaUseCase: RequestTopMangaUseCase,
                                              @IoDispatcher private val ioDispatcher: CoroutineDispatcher
)
    : ViewModel() {

    data class UiState(
        val loading : Boolean = false,
        val animeCategories: List<AnimeCategories>? = null,
        val error : Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init{
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch(ioDispatcher) {

            withContext(Dispatchers.Main){_state.value = UiState(loading = true)}

            val deferredAnimesSeasonNow = async { requestSeasonNowUseCase() }
            val deferredGetTopAnime = async { requestTopAnimeUseCase() }
            val deferredGetTopManga = async { requestTopMangaUseCase() }

            val results = awaitAll(deferredAnimesSeasonNow, deferredGetTopAnime, deferredGetTopManga)

            if(results.any { !it.success }){
                withContext(Dispatchers.Main) {
                    _state.value = UiState(error = true)
                }
            }
            else{
                val animeCategoriesList: MutableList<AnimeCategories> = mutableListOf()

                results.forEach { result ->
                    result.animeCategories?.let { animeCategoriesList.addAll(listOf(it)) }
                }

                withContext(Dispatchers.Main){
                    _state.value = UiState(animeCategories = animeCategoriesList)
                }
            }
        }
    }
}
