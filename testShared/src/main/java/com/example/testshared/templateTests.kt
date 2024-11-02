package com.example.testshared

import com.example.topanime.domain.Anime
import com.example.topanime.domain.AnimeCategories
import com.example.topanime.domain.AnimeListResult
import com.example.topanime.domain.LoginResult

val testAnime = Anime(
    id = 0,
    category = "Action",
    duration = "24 min per ep",
    episodes = 12,
    favorites = 12345,
    popularity = 6789,
    rank = 25,
    rating = "PG-13",
    score = 8.5f,
    season = "Spring",
    source = "Manga",
    status = "Finished Airing",
    synopsis = "A young hero embarks on a journey to save the world.",
    title = "Epic Hero Adventure",
    type = "TV",
    url = "https://myanimelist.net/anime/1/Epic_Hero_Adventure",
    year = 2021,
    image = "https://cdn.myanimelist.net/images/anime/1/12345.jpg",
    genre = "Action, Adventure, Fantasy",
    studios = "Studio Ghibli",
    favorite = false
)

val testSeasonNow = listOf(testAnime.copy(id = 1, category = "Season Now"))
val testTopAnime = listOf(testAnime.copy(id = 2, category = "Top Animes"))
val testTopManga = listOf(testAnime.copy(id = 3, category = "Top Mangas"))

val animeListResultSN = AnimeListResult(
    true,
    AnimeCategories(
        testSeasonNow,
        "Season Now"
    )
)

val animeListResultTN = AnimeListResult(
    true,
    AnimeCategories(
        testTopAnime,
        "Top Animes"
    )
)

val animeListResultTM = AnimeListResult(
    true,
    AnimeCategories(
        testTopManga,
        "Top Mangas"
    )
)

val userTest = "user"
val passwordTest = "12345678"
val loginResult = LoginResult(
    true,
    null
)

val animeListResults = listOf(animeListResultSN, animeListResultTN, animeListResultTM)

val animeCategoriesListTest = listOf(    AnimeCategories(
    testSeasonNow,
    "Season Now"
),     AnimeCategories(
    testTopAnime,
    "Top Animes"
),     AnimeCategories(
    testTopManga,
    "Top Mangas"
))