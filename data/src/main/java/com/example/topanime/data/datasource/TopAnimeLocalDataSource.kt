package com.example.topanime.data.datasource

import com.example.topanime.domain.Anime
import kotlinx.coroutines.flow.Flow

interface TopAnimeLocalDataSource {
    val topAnimesFlow : Flow<List<Anime>>
    suspend fun getTopAnimes() : List<Anime>
    suspend fun isEmpty() : Boolean
    suspend fun saves(topAnimes : List<Anime>)
    suspend fun save(topAnime : Anime)
    fun findById(id: Long) : Flow<Anime>
}
