package com.example.topanime.animeList

import app.cash.turbine.test
import com.example.topanime.FakeSeasonNowDataSource
import com.example.topanime.FakeSeasonNowRemoteDataSource
import com.example.topanime.FakeTopAnimeDataSource
import com.example.topanime.FakeTopAnimeServerDataSource
import com.example.topanime.FakeTopMangaDataSource
import com.example.topanime.FakeTopMangaServerDataSource
import com.example.topanime.data.AnimesRepository
import com.example.topanime.domain.Anime
import com.example.topanime.testRules.CoroutinesTestRule
import com.example.topanime.ui.animeList.AnimeListViewModel
import com.example.topanime.usecases.RequestSeasonNowUseCase
import com.example.topanime.usecases.RequestTopAnimeUseCase
import com.example.topanime.usecases.RequestTopMangaUseCase
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import com.example.testshared.*
import com.example.topanime.ui.animeList.AnimeListViewModel.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class AnimeListIntegrationTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeSeasonNowLocalDataSource: FakeSeasonNowDataSource
    private lateinit var fakeSeasonNowRemoteDataSource: FakeSeasonNowRemoteDataSource
    private lateinit var fakeTopAnimeLocalDataSource: FakeTopAnimeDataSource
    private lateinit var fakeTopAnimeRemoteDataSource: FakeTopAnimeServerDataSource
    private lateinit var fakeTopMangaLocalDataSource: FakeTopMangaDataSource
    private lateinit var fakeTopMangaRemoteDataSource: FakeTopMangaServerDataSource

    @Before
    fun setup() {
        fakeSeasonNowLocalDataSource = FakeSeasonNowDataSource()
        fakeSeasonNowRemoteDataSource = FakeSeasonNowRemoteDataSource()
        fakeTopAnimeLocalDataSource = FakeTopAnimeDataSource()
        fakeTopAnimeRemoteDataSource = FakeTopAnimeServerDataSource()
        fakeTopMangaLocalDataSource = FakeTopMangaDataSource()
        fakeTopMangaRemoteDataSource = FakeTopMangaServerDataSource()
    }

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {

        val animeListViewModel = setupViewModel(
            testSeasonNowRemote = testSeasonNow,
            testTopAnimeRemote = testTopAnime,
            testTopMangaRemote = testTopManga
        )

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
    fun `data is loaded from local source when available`() = runTest {

        val animeListViewModel = setupViewModel(
            testSeasonNowLocal = testSeasonNow,
            testTopAnimeLocal = testTopAnime,
            testTopMangaLocal = testTopManga
        )

        animeListViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(UiState(loading = true), awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(UiState(animeCategories = animeCategoriesListTest), awaitItem())
            cancel()
        }
    }

    private suspend fun setupViewModel(
        testSeasonNowRemote: List<Anime> = emptyList(),
        testTopAnimeRemote: List<Anime> = emptyList(),
        testTopMangaRemote: List<Anime> = emptyList(),
        testSeasonNowLocal: List<Anime> = emptyList(),
        testTopAnimeLocal: List<Anime> = emptyList(),
        testTopMangaLocal: List<Anime> = emptyList()
    ): AnimeListViewModel {
        // Set remote data
        fakeSeasonNowRemoteDataSource.setFakeAnimeList(testSeasonNowRemote)
        fakeTopAnimeRemoteDataSource.setFakeAnimeList(testTopAnimeRemote)
        fakeTopMangaRemoteDataSource.setFakeMangaList(testTopMangaRemote)

        // Set local data
        fakeSeasonNowLocalDataSource.saves(testSeasonNowLocal)
        fakeTopAnimeLocalDataSource.saves(testTopAnimeLocal)
        fakeTopMangaLocalDataSource.saves(testTopMangaLocal)

        val animesRepository = AnimesRepository(
            seasonNowLocalDataSource = fakeSeasonNowLocalDataSource,
            seasonNowRemoteDataSource = fakeSeasonNowRemoteDataSource,
            topAnimeLocalDataSource = fakeTopAnimeLocalDataSource,
            topAnimeRemoteDataSource = fakeTopAnimeRemoteDataSource,
            topMangaLocalDataSource = fakeTopMangaLocalDataSource,
            topMangaRemoteDataSource = fakeTopMangaRemoteDataSource
        )

        val requestSeasonNowUseCase = RequestSeasonNowUseCase(animesRepository)
        val requestTopAnimeUseCase = RequestTopAnimeUseCase(animesRepository)
        val requestTopMangaUseCase = RequestTopMangaUseCase(animesRepository)

        return AnimeListViewModel(
            requestSeasonNowUseCase,
            requestTopAnimeUseCase,
            requestTopMangaUseCase,
            testDispatcher
        )
    }
}
