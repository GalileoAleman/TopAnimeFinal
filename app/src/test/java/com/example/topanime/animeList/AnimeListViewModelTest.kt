package com.example.topanime.animeList

import app.cash.turbine.test
import com.example.testshared.*
import com.example.topanime.testRules.CoroutinesTestRule
import com.example.topanime.ui.animeList.AnimeListViewModel
import com.example.topanime.ui.animeList.AnimeListViewModel.UiState
import com.example.topanime.usecases.RequestSeasonNowUseCase
import com.example.topanime.usecases.RequestTopAnimeUseCase
import com.example.topanime.usecases.RequestTopMangaUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AnimeListViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var requestSeasonNowUseCase: RequestSeasonNowUseCase

    @Mock
    lateinit var requestTopAnimeUseCase: RequestTopAnimeUseCase

    @Mock
    lateinit var requestTopMangaUseCase: RequestTopMangaUseCase

    private lateinit var animeListViewModel: AnimeListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `State is updated with current cached content immediately Animes Categories`() =
        runTest {
            // GIVEN
            whenever(requestSeasonNowUseCase()).thenReturn(animeListResultSN)
            whenever(requestTopAnimeUseCase()).thenReturn(animeListResultTN)
            whenever(requestTopMangaUseCase()).thenReturn(animeListResultTM)
            //WHEN
            animeListViewModel = AnimeListViewModel(requestSeasonNowUseCase, requestTopAnimeUseCase
                , requestTopMangaUseCase, testDispatcher)
            // THEN
            animeListViewModel.state.test {
                assertEquals(UiState(), awaitItem())
                testDispatcher.scheduler.advanceUntilIdle()
                assertEquals(UiState(loading = true), awaitItem())
                testDispatcher.scheduler.advanceUntilIdle()
                assertEquals(UiState(animeCategories = animeCategoriesListTest), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Progress is shown when screen starts and hidden when it finishes requesting Animes Categories`() =
        runTest {
            // GIVEN
            whenever(requestSeasonNowUseCase()).thenReturn(animeListResultSN)
            whenever(requestTopAnimeUseCase()).thenReturn(animeListResultTN)
            whenever(requestTopMangaUseCase()).thenReturn(animeListResultTM)
            //WHEN
            animeListViewModel = AnimeListViewModel(requestSeasonNowUseCase, requestTopAnimeUseCase
                , requestTopMangaUseCase, testDispatcher)
            // THEN
            animeListViewModel.state.test {
                assertEquals(UiState(), awaitItem())
                testDispatcher.scheduler.advanceUntilIdle()
                assertEquals(UiState(loading = true), awaitItem())
                testDispatcher.scheduler.advanceUntilIdle()
                assertEquals(UiState(loading = false, animeCategories = animeCategoriesListTest), awaitItem())
                cancel()
            }
        }
}