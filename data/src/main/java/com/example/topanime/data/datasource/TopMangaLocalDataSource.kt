package com.example.topanime.data.datasource

import com.example.topanime.domain.Anime
import kotlinx.coroutines.flow.Flow

interface TopMangaLocalDataSource {
    val topMangasFlow : Flow<List<Anime>>
    suspend fun getTopMangas() : List<Anime>
    suspend fun isEmpty() : Boolean
    suspend fun saves(topMangas : List<Anime>)
    suspend fun save(topManga : Anime)
    fun findById(id: Long) : Flow<Anime>
}
