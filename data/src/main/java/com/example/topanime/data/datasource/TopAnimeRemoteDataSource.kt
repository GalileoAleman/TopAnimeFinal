package com.example.topanime.data.datasource

import com.example.topanime.domain.Anime

interface TopAnimeRemoteDataSource {
    suspend fun requestTopAnimes(): List<Anime>
}
