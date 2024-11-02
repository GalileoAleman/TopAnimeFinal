package com.example.topanime.data.datasource

import com.example.topanime.domain.Anime
import kotlinx.coroutines.flow.Flow

interface SeasonNowLocalDataSource {
    val seasonsNowFlow : Flow<List<Anime>>
    suspend fun getSeasons() : List<Anime>
    suspend fun isEmpty() : Boolean
    suspend fun saves(seasonsNow : List<Anime>)
    suspend fun save(seasonNow : Anime)
    fun findById(id: Long) : Flow<Anime>
}
