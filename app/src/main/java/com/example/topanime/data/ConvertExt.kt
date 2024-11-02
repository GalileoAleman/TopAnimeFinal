package com.example.topanime.data

import com.example.topanime.data.database.SeasonNow
import com.example.topanime.data.database.TopAnime
import com.example.topanime.data.database.TopManga
import com.example.topanime.data.server.remote.AnimeRemote
import com.example.topanime.domain.Anime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun AnimeRemote.toLocalModelSeasonNow() : SeasonNow = SeasonNow(
    id.toLong(),
    category = "Season Now",
    duration,
    episodes,
    favorites,
    popularity,
    rank,
    rating,
    score,
    season,
    source,
    status,
    synopsis,
    title,
    type,
    url,
    year,
    image = images.jpg.large_image_url,
    genre = genres.joinToString(separator = "-", transform = { it.name }),
    studios = studios?.get(0)?.name,
    favorite = false
)

fun List<AnimeRemote>.toLocalModelSeasonNow(): List<SeasonNow> = map { it.toLocalModelSeasonNow() }

fun AnimeRemote.toLocalModelTopAnime() : TopAnime = TopAnime(
    id.toLong(),
    category = "Top Animes",
    duration,
    episodes,
    favorites,
    popularity,
    rank,
    rating,
    score,
    season,
    source,
    status,
    synopsis,
    title,
    type,
    url,
    year,
    image = images.jpg.large_image_url,
    genre = genres.joinToString(separator = "-", transform = { it.name }),
    studios = studios?.get(0)?.name,
    favorite = false
)

fun List<AnimeRemote>.toLocalModelTopAnime(): List<TopAnime> = map { it.toLocalModelTopAnime() }

fun AnimeRemote.toLocalModelTopManga() : TopManga = TopManga(
    id.toLong(),
    category = "Top Mangas",
    duration,
    episodes,
    favorites,
    popularity,
    rank,
    rating,
    score,
    season,
    source,
    status,
    synopsis,
    title,
    type,
    url,
    year,
    image = images.jpg.large_image_url,
    genre = genres.joinToString(separator = "-", transform = { it.name }),
    studios = studios?.get(0)?.name,
    favorite = false
)

fun List<AnimeRemote>.toLocalModelTopManga(): List<TopManga> = map { it.toLocalModelTopManga() }

fun SeasonNow.seasonToAnime() : Anime =
    Anime(
        id,
        category,
        duration,
        episodes,
        favorites,
        popularity,
        rank,
        rating,
        score,
        season,
        source,
        status,
        synopsis,
        title,
        type,
        url,
        year,
        image,
        genre,
        studios,
        favorite
    )

fun List<SeasonNow>.seasonToAnime(): List<Anime> = map { it.seasonToAnime() }
fun Flow<SeasonNow>.seasonToAnime(): Flow<Anime> = map { it.seasonToAnime() }

fun TopAnime.topToAnime() : Anime = Anime(
    id,
    category,
    duration,
    episodes,
    favorites,
    popularity,
    rank,
    rating,
    score,
    season,
    source,
    status,
    synopsis,
    title,
    type,
    url,
    year,
    image,
    genre,
    studios,
    favorite
)

fun List<TopAnime>.topToAnime(): List<Anime> = map { it.topToAnime() }
fun Flow<TopAnime>.topToAnime(): Flow<Anime> = map { it.topToAnime() }

fun TopManga.mangaToAnime() : Anime = Anime(
    id,
    category,
    duration,
    episodes,
    favorites,
    popularity,
    rank,
    rating,
    score,
    season,
    source,
    status,
    synopsis,
    title,
    type,
    url,
    year,
    image,
    genre,
    studios,
    favorite
)

fun List<TopManga>.mangaToAnime(): List<Anime> = map { it.mangaToAnime() }
fun Flow<TopManga>.mangaToAnime(): Flow<Anime> = map { it.mangaToAnime() }

fun Anime.animeToSeason() : SeasonNow = SeasonNow(
    id,
    category,
    duration,
    episodes,
    favorites,
    popularity,
    rank,
    rating,
    score,
    season,
    source,
    status,
    synopsis,
    title,
    type,
    url,
    year,
    image,
    genre,
    studios,
    favorite
)

fun List<Anime>.animeToSeason(): List<SeasonNow> = map { it.animeToSeason() }

fun Anime.animeToTop() : TopAnime = TopAnime(
    id,
    category,
    duration,
    episodes,
    favorites,
    popularity,
    rank,
    rating,
    score,
    season,
    source,
    status,
    synopsis,
    title,
    type,
    url,
    year,
    image,
    genre,
    studios,
    favorite
)

fun List<Anime>.animeToTop(): List<TopAnime> = map { it.animeToTop() }

fun Anime.animeToManga() : TopManga = TopManga(
    id,
    category,
    duration,
    episodes,
    favorites,
    popularity,
    rank,
    rating,
    score,
    season,
    source,
    status,
    synopsis,
    title,
    type,
    url,
    year,
    image,
    genre,
    studios,
    favorite
)

fun List<Anime>.animeToManga(): List<TopManga> = map { it.animeToManga() }
