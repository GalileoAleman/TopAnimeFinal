package com.example.topanime.data.datasource

import com.example.topanime.domain.Anime

interface SeasonNowRemoteDataSource {
    suspend fun requestSeasonNow(): List<Anime>
}
