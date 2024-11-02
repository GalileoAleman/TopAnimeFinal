package com.example.topanime.data.database

import com.example.topanime.data.datasource.SeasonNowLocalDataSource
import com.example.topanime.data.animeToSeason
import com.example.topanime.data.seasonToAnime
import com.example.topanime.domain.Anime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeasonNowRoomDataSource @Inject constructor(private val seasonNowDao : SeasonNowDao) : SeasonNowLocalDataSource {

    override val seasonsNowFlow : Flow<List<Anime>> = seasonNowDao.getAllF().map { it.seasonToAnime() }
    override suspend fun getSeasons() : List<Anime> = seasonNowDao.getAll().map { it.seasonToAnime() }
    override suspend fun isEmpty() : Boolean = seasonNowDao.seasonNowCount() == 0
    override suspend fun saves(animes : List<Anime>){
        seasonNowDao.insertSeasonsNow(animes.animeToSeason())
    }
    override suspend fun save(anime : Anime){
        seasonNowDao.insertSeasonNow(anime.animeToSeason())
    }
    override fun findById(id: Long) : Flow<Anime> = seasonNowDao.findById(id).map { it.seasonToAnime() }
}
