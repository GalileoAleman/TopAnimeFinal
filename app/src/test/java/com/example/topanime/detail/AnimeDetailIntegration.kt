package com.example.topanime.detail

import app.cash.turbine.test
import com.example.testshared.testAnime
import com.example.testshared.testSeasonNow
import com.example.testshared.testTopAnime
import com.example.testshared.testTopManga
import com.example.topanime.FakeSeasonNowDataSource
import com.example.topanime.FakeSeasonNowRemoteDataSource
import com.example.topanime.FakeTopAnimeDataSource
import com.example.topanime.FakeTopAnimeServerDataSource
import com.example.topanime.FakeTopMangaDataSource
import com.example.topanime.FakeTopMangaServerDataSource
import com.example.topanime.data.AnimesRepository
import com.example.topanime.domain.Anime
import com.example.topanime.testRules.CoroutinesTestRule
import com.example.topanime.ui.detail.AnimeDetailViewModel
import com.example.topanime.ui.detail.AnimeDetailViewModel.UiState
import com.example.topanime.usecases.FindAnimeUseCase
import com.example.topanime.usecases.SwitchAnimeFavUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AnimeDetailIntegration {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var fakeSeasonNowLocalDataSource: FakeSeasonNowDataSource
    private lateinit var fakeSeasonNowRemoteDataSource: FakeSeasonNowRemoteDataSource
    private lateinit var fakeTopAnimeLocalDataSource: FakeTopAnimeDataSource
    private lateinit var fakeTopAnimeRemoteDataSource: FakeTopAnimeServerDataSource
    private lateinit var fakeTopMangaLocalDataSource: FakeTopMangaDataSource
    private lateinit var fakeTopMangaRemoteDataSource: FakeTopMangaServerDataSource

    private val seasonNow = testAnime.copy(id = 1, category = "Season Now")
    private val topAnime = testAnime.copy(id = 2, category = "Top Animes")
    private val topManga = testAnime.copy(id = 3, category = "Top Mangas")

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
    fun `UI is updated with the anime Season Now on start`() = runTest {

        val animeDetailViewModel = setupViewModel(
            1,
            "Season Now",
            testSeasonNowLocal = testSeasonNow,
            testTopAnimeLocal = testTopAnime,
            testTopMangaLocal = testTopManga
        )

        animeDetailViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(animeDetail = seasonNow), awaitItem())
            cancel()
        }
    }

    @Test
    fun `UI is updated with the anime Top Anime on start`() = runTest {
        val animeDetailViewModel = setupViewModel(
            2,
            "Top Animes",
            testSeasonNowLocal = testSeasonNow,
            testTopAnimeLocal = testTopAnime,
            testTopMangaLocal = testTopManga
        )

        animeDetailViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(animeDetail = topAnime), awaitItem())
            cancel()
        }
    }

    @Test
    fun `UI is updated with the anime Top Mangas on start`() = runTest {
        val animeDetailViewModel = setupViewModel(
            3,
            "Top Mangas",
            testSeasonNowLocal = testSeasonNow,
            testTopAnimeLocal = testTopAnime,
            testTopMangaLocal = testTopManga
        )

        animeDetailViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(animeDetail = topManga), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        val animeDetailViewModel = setupViewModel(
            1,
            "Season Now",
            testSeasonNowLocal = testSeasonNow,
            testTopAnimeLocal = testTopAnime,
            testTopMangaLocal = testTopManga
        )

        animeDetailViewModel.onFavClicked()

        animeDetailViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(animeDetail = seasonNow), awaitItem())
            assertEquals(UiState(animeDetail = seasonNow.copy(favorite = !seasonNow.favorite)), awaitItem())
            cancel()
        }
    }

    private suspend fun setupViewModel(
        animeId : Long,
        animeCat : String,
        testSeasonNowRemote: List<Anime> = emptyList(),
        testTopAnimeRemote: List<Anime> = emptyList(),
        testTopMangaRemote: List<Anime> = emptyList(),
        testSeasonNowLocal: List<Anime> = emptyList(),
        testTopAnimeLocal: List<Anime> = emptyList(),
        testTopMangaLocal: List<Anime> = emptyList()
    ): AnimeDetailViewModel {
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

        val findAnimeUseCase = FindAnimeUseCase(animesRepository)
        val switchAnimeFavUseCase = SwitchAnimeFavUseCase(animesRepository)

        return AnimeDetailViewModel(
            animeId,
            animeCat,
            findAnimeUseCase,
            switchAnimeFavUseCase
        )
    }
}