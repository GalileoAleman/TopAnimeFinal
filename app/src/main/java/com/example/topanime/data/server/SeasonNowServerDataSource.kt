package com.example.topanime.data.server

import com.example.topanime.data.datasource.SeasonNowRemoteDataSource
import com.example.topanime.data.seasonToAnime
import com.example.topanime.data.toLocalModelSeasonNow
import javax.inject.Inject

class SeasonNowServerDataSource @Inject constructor(private val jikanRestAnimeDbService: JikanRestAnimeDbService) : SeasonNowRemoteDataSource {
    override suspend fun requestSeasonNow() = jikanRestAnimeDbService.getAnimesSeasonNow().data.toLocalModelSeasonNow().seasonToAnime()
}
