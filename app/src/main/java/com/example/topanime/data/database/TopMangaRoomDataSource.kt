package com.example.topanime.data.database

import com.example.topanime.data.datasource.TopMangaLocalDataSource
import com.example.topanime.domain.Anime
import com.example.topanime.data.animeToManga
import com.example.topanime.data.mangaToAnime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopMangaRoomDataSource @Inject constructor(private val topMangaDao: TopMangaDao) : TopMangaLocalDataSource {

    override val topMangasFlow : Flow<List<Anime>> = topMangaDao.getAllF().map { it.mangaToAnime() }
    override suspend fun getTopMangas() : List<Anime> = topMangaDao.getAll().map { it.mangaToAnime() }
    override suspend fun isEmpty() : Boolean = topMangaDao.topMangaCount() == 0
    override suspend fun saves(animes : List<Anime>){
        topMangaDao.insertTopMangas(animes.animeToManga())
    }
    override suspend fun save(anime : Anime){
        topMangaDao.insertTopManga(anime.animeToManga())
    }
    override fun findById(id: Long) : Flow<Anime> = topMangaDao.findById(id).map { it.mangaToAnime() }
}
