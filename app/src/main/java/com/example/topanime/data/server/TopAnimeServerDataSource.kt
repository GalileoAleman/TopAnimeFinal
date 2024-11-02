package com.example.topanime.data.server

import com.example.topanime.data.datasource.TopAnimeRemoteDataSource
import com.example.topanime.data.toLocalModelTopAnime
import com.example.topanime.data.topToAnime
import javax.inject.Inject

class TopAnimeServerDataSource @Inject constructor(private val jikanRestAnimeDbService: JikanRestAnimeDbService) : TopAnimeRemoteDataSource {
    override suspend fun requestTopAnimes() = jikanRestAnimeDbService.getTopAnimne().data.toLocalModelTopAnime().topToAnime()
}
