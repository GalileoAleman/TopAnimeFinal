package com.example.topanime.detail

import app.cash.turbine.test
import com.example.testshared.*
import com.example.topanime.testRules.CoroutinesTestRule
import com.example.topanime.ui.detail.AnimeDetailViewModel
import com.example.topanime.ui.detail.AnimeDetailViewModel.*
import com.example.topanime.usecases.FindAnimeUseCase
import com.example.topanime.usecases.SwitchAnimeFavUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import org.junit.Assert.*
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AnimeDetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findAnimeUseCase: FindAnimeUseCase

    @Mock
    lateinit var switchAnimeFavUseCase: SwitchAnimeFavUseCase

    private lateinit var animeDetailViewModel : AnimeDetailViewModel

    private val seasonNow = testAnime.copy(id = 1, category = "Season Now")
    private val topAnime = testAnime.copy(id = 2, category = "Top Animes")
    private val topManga = testAnime.copy(id = 3, category = "Top Mangas")

    @Test
    fun `UI is updated with the anime Season Now on start`() = runTest {
        // GIVEN
        whenever(findAnimeUseCase(1, "Season Now")).thenReturn(flowOf(seasonNow))
        // WHEN
        animeDetailViewModel = AnimeDetailViewModel(1, "Season Now",
            findAnimeUseCase, switchAnimeFavUseCase)
        // THEN
        animeDetailViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(animeDetail = seasonNow), awaitItem())
            cancel()
        }
    }

    @Test
    fun `UI is updated with the anime Top Anime on start`() = runTest {
        // GIVEN
        whenever(findAnimeUseCase(2, "Top Animes")).thenReturn(flowOf(topAnime))
        // WHEN
        animeDetailViewModel = AnimeDetailViewModel(2, "Top Animes",
            findAnimeUseCase, switchAnimeFavUseCase)
        // THEN
        animeDetailViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(animeDetail = topAnime), awaitItem())
            cancel()
        }
    }

    @Test
    fun `UI is updated with the anime Top Mangas on start`() = runTest {
        // GIVEN
        whenever(findAnimeUseCase(3, "Top Mangas")).thenReturn(flowOf(topManga))
        // WHEN
        animeDetailViewModel = AnimeDetailViewModel(3, "Top Mangas",
            findAnimeUseCase, switchAnimeFavUseCase)
        // THEN
        animeDetailViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(animeDetail = topManga), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        // GIVEN
        whenever(findAnimeUseCase(1, "Season Now")).thenReturn(flowOf(seasonNow))
        // WHEN
        animeDetailViewModel = AnimeDetailViewModel(1, "Season Now",
            findAnimeUseCase, switchAnimeFavUseCase)
        animeDetailViewModel.onFavClicked()
        // THEN
        runCurrent()
        verify(switchAnimeFavUseCase).invoke(seasonNow)
    }
}