package com.example.topanime.data

import com.example.topanime.data.datasource.*
import com.example.topanime.domain.Anime
import com.example.topanime.domain.AnimeCategories
import com.example.topanime.domain.AnimeListResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimesRepository @Inject constructor(private val seasonNowLocalDataSource: SeasonNowLocalDataSource, private val seasonNowRemoteDataSource: SeasonNowRemoteDataSource,
                                           private val topAnimeLocalDataSource: TopAnimeLocalDataSource, private val topAnimeRemoteDataSource: TopAnimeRemoteDataSource,
                                           private val topMangaLocalDataSource: TopMangaLocalDataSource, private val topMangaRemoteDataSource: TopMangaRemoteDataSource) {

    private val seasonNow = "Season Now"
    private val topAnimes = "Top Animes"
    private val topMangas = "Top Mangas"

    suspend fun getSeasonNow() : AnimeListResult {
        return runCatching {

            if(seasonNowLocalDataSource.isEmpty()){
                seasonNowLocalDataSource.saves(seasonNowRemoteDataSource.requestSeasonNow())
            }

            seasonNowLocalDataSource.getSeasons()
        }.toAnimeListResult(seasonNow)
    }

    suspend fun getTopAnimes() : AnimeListResult {
        return runCatching {

            if(topAnimeLocalDataSource.isEmpty()){
                topAnimeLocalDataSource.saves(topAnimeRemoteDataSource.requestTopAnimes())
            }

            topAnimeLocalDataSource.getTopAnimes()
        }.toAnimeListResult(topAnimes)
    }

    suspend fun getTopsMangas() : AnimeListResult {
        return runCatching {

            if(topMangaLocalDataSource.isEmpty()){
                topMangaLocalDataSource.saves(topMangaRemoteDataSource.requestTopsMangas())
            }

            topMangaLocalDataSource.getTopMangas()
        }.toAnimeListResult(topMangas)
    }

    suspend fun switchFav(anime: Anime) {
        val updatedAnime = anime.copy(favorite = !anime.favorite)

        when(anime.category){
            seasonNow -> seasonNowLocalDataSource.save(updatedAnime)
            topAnimes -> topAnimeLocalDataSource.save(updatedAnime)
            topMangas -> topMangaLocalDataSource.save(updatedAnime)
        }
    }

    fun findById(animeId: Long, animeCat: String) : Flow<Anime>? = when(animeCat){
            seasonNow -> seasonNowLocalDataSource.findById(animeId)
            topAnimes -> topAnimeLocalDataSource.findById(animeId)
            topMangas -> topMangaLocalDataSource.findById(animeId)
            else -> null
        }

    private fun Result<List<Anime>>.toAnimeListResult(type : String) = when (val result: List<Anime>? = getOrNull()) {
        null -> AnimeListResult(false, null)
        else -> {
            AnimeListResult(true, AnimeCategories(result, type))
        }
    }
}
