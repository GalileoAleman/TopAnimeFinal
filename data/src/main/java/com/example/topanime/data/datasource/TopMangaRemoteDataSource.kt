package com.example.topanime.data.datasource

import com.example.topanime.domain.Anime

interface TopMangaRemoteDataSource {
    suspend fun requestTopsMangas(): List<Anime>
}
