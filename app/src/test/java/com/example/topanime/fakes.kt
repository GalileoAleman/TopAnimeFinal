package com.example.topanime

import com.example.topanime.data.common.IValidator
import com.example.topanime.data.datasource.SeasonNowLocalDataSource
import com.example.topanime.data.datasource.SeasonNowRemoteDataSource
import com.example.topanime.data.datasource.TopAnimeLocalDataSource
import com.example.topanime.data.datasource.TopAnimeRemoteDataSource
import com.example.topanime.data.datasource.TopMangaLocalDataSource
import com.example.topanime.data.datasource.TopMangaRemoteDataSource
import com.example.topanime.data.datasource.firebase.FirebaseRemoteDataSource
import com.example.topanime.domain.Anime
import com.example.topanime.domain.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeSeasonNowDataSource : SeasonNowLocalDataSource {

    private val seasonsNowList = mutableListOf<Anime>()
    private val _seasonsNowFlow = MutableStateFlow(seasonsNowList.toList())

    override val seasonsNowFlow: Flow<List<Anime>>
        get() = _seasonsNowFlow

    override suspend fun getSeasons(): List<Anime> {
        return seasonsNowList.toList()
    }

    override suspend fun isEmpty(): Boolean {
        return seasonsNowList.isEmpty()
    }

    override suspend fun saves(seasonsNow: List<Anime>) {
        seasonsNowList.clear()
        seasonsNowList.addAll(seasonsNow)
        _seasonsNowFlow.update { seasonsNowList.toList() }
    }

    override suspend fun save(seasonNow: Anime) {
        val index = seasonsNowList.indexOfFirst { it.id == seasonNow.id }
        if (index != -1) {
            seasonsNowList[index] = seasonNow
        } else {
            seasonsNowList.add(seasonNow)
        }
        _seasonsNowFlow.update { seasonsNowList.toList() }
    }

    override fun findById(id: Long): Flow<Anime> {
        return _seasonsNowFlow.map { seasons ->
            seasons.first { it.id == id }
        }
    }
}

class FakeSeasonNowRemoteDataSource : SeasonNowRemoteDataSource {

    private var fakeAnimeList: List<Anime> = emptyList()

    fun setFakeAnimeList(animes: List<Anime>) {
        fakeAnimeList = animes
    }

    override suspend fun requestSeasonNow(): List<Anime> {
        return fakeAnimeList
    }
}

class FakeTopAnimeDataSource : TopAnimeLocalDataSource {

    private val topAnimesList = mutableListOf<Anime>()
    private val _topAnimesFlow = MutableStateFlow(topAnimesList.toList())

    override val topAnimesFlow: Flow<List<Anime>>
        get() = _topAnimesFlow

    override suspend fun getTopAnimes(): List<Anime> {
        return topAnimesList.toList()
    }

    override suspend fun isEmpty(): Boolean {
        return topAnimesList.isEmpty()
    }

    override suspend fun saves(topAnimes: List<Anime>) {
        topAnimesList.clear()
        topAnimesList.addAll(topAnimes)
        _topAnimesFlow.update { topAnimesList.toList() }
    }

    override suspend fun save(topAnime: Anime) {
        topAnimesList.add(topAnime)
        _topAnimesFlow.update { topAnimesList.toList() }
    }

    override fun findById(id: Long): Flow<Anime> {
        return _topAnimesFlow.map { animes ->
            animes.first { it.id == id }
        }
    }
}

class FakeTopAnimeServerDataSource : TopAnimeRemoteDataSource {

    private var fakeAnimeList: List<Anime> = emptyList()

    fun setFakeAnimeList(animes: List<Anime>) {
        fakeAnimeList = animes
    }

    override suspend fun requestTopAnimes(): List<Anime> {
        return fakeAnimeList
    }
}

class FakeTopMangaDataSource : TopMangaLocalDataSource {

    private val topMangasList = mutableListOf<Anime>()
    private val _topMangasFlow = MutableStateFlow(topMangasList.toList())

    override val topMangasFlow: Flow<List<Anime>>
        get() = _topMangasFlow

    override suspend fun getTopMangas(): List<Anime> {
        return topMangasList.toList()
    }

    override suspend fun isEmpty(): Boolean {
        return topMangasList.isEmpty()
    }

    override suspend fun saves(topMangas: List<Anime>) {
        topMangasList.clear()
        topMangasList.addAll(topMangas)
        _topMangasFlow.update { topMangasList.toList() }
    }

    override suspend fun save(topManga: Anime) {
        topMangasList.add(topManga)
        _topMangasFlow.update { topMangasList.toList() }
    }

    override fun findById(id: Long): Flow<Anime> {
        return _topMangasFlow.map { mangas ->
            mangas.first { it.id == id }
        }
    }
}

class FakeTopMangaServerDataSource : TopMangaRemoteDataSource {

    private var fakeMangaList: List<Anime> = emptyList()

    fun setFakeMangaList(mangas: List<Anime>) {
        fakeMangaList = mangas
    }

    override suspend fun requestTopsMangas(): List<Anime> {
        return fakeMangaList
    }
}

class FakeFirebaseServerDataSource : FirebaseRemoteDataSource {

    private var fakeCurrentUser: Boolean = false

    override suspend fun login(email: String, password: String): LoginResult {
        return if (email == "test@gmail.com" && password == "12346578") {
            fakeCurrentUser = true
            LoginResult(true, null)
        } else {
            LoginResult(false, "Error test login")
        }
    }

    override suspend fun signin(email: String, password: String): LoginResult {
        return if (email == "test@gmail.com" && password == "12346578") {
            fakeCurrentUser = true
            LoginResult(true, null)
        } else {
            LoginResult(false, "Error test login")
        }
    }

    override fun signout() {
        fakeCurrentUser = false
    }

    override suspend fun getCurrentUser(): Boolean {
        return fakeCurrentUser
    }
}

class FakeValidator : IValidator {

    override fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return email.matches(emailRegex)
    }

    override fun isValidPassword(password: String): Boolean {
        return password.length > 5
    }
}