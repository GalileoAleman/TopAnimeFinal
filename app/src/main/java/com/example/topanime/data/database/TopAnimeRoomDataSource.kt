package com.example.topanime.data.database

import com.example.topanime.data.datasource.TopAnimeLocalDataSource
import com.example.topanime.domain.Anime
import com.example.topanime.data.animeToTop
import com.example.topanime.data.topToAnime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopAnimeRoomDataSource @Inject constructor(private val topAnimeDao: TopAnimeDao) : TopAnimeLocalDataSource {

    override val topAnimesFlow : Flow<List<Anime>> = topAnimeDao.getAllF().map { it.topToAnime() }
    override suspend fun getTopAnimes() : List<Anime> = topAnimeDao.getAll().map { it.topToAnime() }
    override suspend fun isEmpty() : Boolean = topAnimeDao.topAnimeCount() == 0
    override suspend fun saves(animes : List<Anime>){
        topAnimeDao.insertTopAnimes(animes.animeToTop())
    }
    override suspend fun save(anime : Anime){
        topAnimeDao.insertTopAnime(anime.animeToTop())
    }
    override fun findById(id: Long) : Flow<Anime> = topAnimeDao.findById(id).map { it.topToAnime() }
}
