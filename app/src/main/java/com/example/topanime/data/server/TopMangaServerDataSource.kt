package com.example.topanime.data.server

import com.example.topanime.data.datasource.TopMangaRemoteDataSource
import com.example.topanime.data.mangaToAnime
import com.example.topanime.data.toLocalModelTopManga
import javax.inject.Inject

class TopMangaServerDataSource @Inject constructor(private val jikanRestAnimeDbService: JikanRestAnimeDbService) : TopMangaRemoteDataSource {
    override suspend fun requestTopsMangas() = jikanRestAnimeDbService.getTopManga().data.toLocalModelTopManga().mangaToAnime()
}
