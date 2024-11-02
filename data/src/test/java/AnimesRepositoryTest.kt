package com.example.topanime.data

import com.example.testshared.*
import com.example.topanime.data.datasource.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AnimesRepositoryTest {

    @Mock
    lateinit var seasonNowLocalDataSource: SeasonNowLocalDataSource

    @Mock
    lateinit var seasonNowRemoteDataSource: SeasonNowRemoteDataSource

    @Mock
    lateinit var topAnimeLocalDataSource: TopAnimeLocalDataSource

    @Mock
    lateinit var topAnimeRemoteDataSource: TopAnimeRemoteDataSource

    @Mock
    lateinit var topMangaLocalDataSource: TopMangaLocalDataSource

    @Mock
    lateinit var topMangaRemoteDataSource: TopMangaRemoteDataSource

    lateinit var animeRepository: AnimesRepository

    @Before
    fun setUp() {
        animeRepository = AnimesRepository(
            seasonNowLocalDataSource,
            seasonNowRemoteDataSource,
            topAnimeLocalDataSource,
            topAnimeRemoteDataSource,
            topMangaLocalDataSource,
            topMangaRemoteDataSource
        )
    }

    @Test
    fun `Season Now are taken from local data source if available`() = runBlocking{
        // GIVEN
        whenever(seasonNowLocalDataSource.isEmpty()).thenReturn(false)
        whenever(seasonNowLocalDataSource.getSeasons()).thenReturn(testSeasonNow)
        // WHEN
        val result = animeRepository.getSeasonNow()
        // THEN
        assertEquals(animeListResultSN, result)
    }

    @Test
    fun `Top Anime are taken from local data source if available`() = runBlocking{
        // GIVEN
        whenever(topAnimeLocalDataSource.isEmpty()).thenReturn(false)
        whenever(topAnimeLocalDataSource.getTopAnimes()).thenReturn(testTopAnime)
        // WHEN
        val result = animeRepository.getTopAnimes()
        // THEN
        assertEquals(animeListResultTN, result)
    }

    @Test
    fun `Top Manga are taken from local data source if available`() = runBlocking{
        // GIVEN
        whenever(topMangaLocalDataSource.isEmpty()).thenReturn(false)
        whenever(topMangaLocalDataSource.getTopMangas()).thenReturn(testTopManga)
        // WHEN
        val result = animeRepository.getTopsMangas()
        // THEN
        assertEquals(animeListResultTM, result)
    }

    @Test
    fun `Season Now are saved to local data source when it's empty`() = runBlocking{
        // GIVEN
        whenever(seasonNowLocalDataSource.isEmpty()).thenReturn(true)
        whenever(seasonNowRemoteDataSource.requestSeasonNow()).thenReturn(testSeasonNow)
        // WHEN
        animeRepository.getSeasonNow()
        // THEN
        verify(seasonNowLocalDataSource).saves(testSeasonNow)
    }

    @Test
    fun `Top Anime are saved to local data source when it's empty`() = runBlocking{
        // GIVEN
        whenever(topAnimeLocalDataSource.isEmpty()).thenReturn(true)
        whenever(topAnimeRemoteDataSource.requestTopAnimes()).thenReturn(testTopAnime)
        // WHEN
        animeRepository.getTopAnimes()
        // THEN
        verify(topAnimeLocalDataSource).saves(testTopAnime)
    }

    @Test
    fun `Top Manga are saved to local data source when it's empty`() = runBlocking{
        // GIVEN
        whenever(topMangaLocalDataSource.isEmpty()).thenReturn(true)
        whenever(topMangaRemoteDataSource.requestTopsMangas()).thenReturn(testTopManga)
        // WHEN
        animeRepository.getTopsMangas()
        // THEN
        verify(topMangaLocalDataSource).saves(testTopManga)
    }

    @Test
    fun `Finding a anime by id is done in local Season Now data source`(): Unit = runBlocking {
        // GIVEN
        val testAnimeSN = flowOf(testAnime.copy(id = 4, category = "Season Now"))
        whenever(seasonNowLocalDataSource.findById(4)).thenReturn(testAnimeSN)
        // WHEN
        val result = animeRepository.findById(4, "Season Now")
        // THEN
        assertEquals(result, testAnimeSN)
    }

    @Test
    fun `Finding a anime by id is done in local Top Anime data source`(): Unit = runBlocking {
        // GIVEN
        val testAnimeTA = flowOf(testAnime.copy(id = 5, category = "Top Animes"))
        whenever(topAnimeLocalDataSource.findById(5)).thenReturn(testAnimeTA)
        // WHEN
        val result = animeRepository.findById(5, "Top Animes")
        // THEN
        assertEquals(result, testAnimeTA)
    }

    @Test
    fun `Finding a anime by id is done in local Top Manga data source`(): Unit = runBlocking {
        // GIVEN
        val testAnimeTM = flowOf(testAnime.copy(id = 6, category = "Top Mangas"))
        whenever(topMangaLocalDataSource.findById(6)).thenReturn(testAnimeTM)
        // WHEN
        val result = animeRepository.findById(6, "Top Mangas")
        // THEN
        assertEquals(result, testAnimeTM)
    }

    @Test
    fun `Switching favorite marks as favorite a unfavorite Season Now anime`(): Unit = runBlocking {        // GIVEN
        // GIVEN
        val anime = testAnime.copy(category = "Season Now", favorite = false)
        // WHEN
        animeRepository.switchFav(anime)
        // ENTONCES
        verify(seasonNowLocalDataSource).save(argThat { favorite })
    }

    @Test
    fun `Switching favorite marks as favorite a unfavorite Top Animes anime`(): Unit = runBlocking {        // GIVEN
        // GIVEN
        val anime = testAnime.copy(category = "Top Animes", favorite = false)
        // WHEN
        animeRepository.switchFav(anime)
        // ENTONCES
        verify(topAnimeLocalDataSource).save(argThat { favorite })
    }

    @Test
    fun `Switching favorite marks as favorite a unfavorite Top Mangas anime`(): Unit = runBlocking {        // GIVEN
        // GIVEN
        val anime = testAnime.copy(category = "Top Mangas", favorite = false)
        // WHEN
        animeRepository.switchFav(anime)
        // ENTONCES
        verify(topMangaLocalDataSource).save(argThat { favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite Season Now anime`(): Unit = runBlocking {        // GIVEN
        // GIVEN
        val anime = testAnime.copy(category = "Season Now", favorite = true)
        // WHEN
        animeRepository.switchFav(anime)
        // ENTONCES
        verify(seasonNowLocalDataSource).save(argThat { !favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite Top Animes anime`(): Unit = runBlocking {        // GIVEN
        // GIVEN
        val anime = testAnime.copy(category = "Top Animes", favorite = true)
        // WHEN
        animeRepository.switchFav(anime)
        // ENTONCES
        verify(topAnimeLocalDataSource).save(argThat { !favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite Top Mangas anime`(): Unit = runBlocking {        // GIVEN
        // GIVEN
        val anime = testAnime.copy(category = "Top Mangas", favorite = true)
        // WHEN
        animeRepository.switchFav(anime)
        // ENTONCES
        verify(topMangaLocalDataSource).save(argThat { !favorite })
    }
}
