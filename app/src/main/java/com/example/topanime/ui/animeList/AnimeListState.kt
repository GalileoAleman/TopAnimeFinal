package com.example.topanime.ui.animeList

import androidx.navigation.NavController
import com.example.topanime.domain.Anime

class AnimeListState(private val navController: NavController) {
    fun onAnimeClicked(anime: Anime) {
        val navAction = AnimeListFragmentDirections.actionAnimeListToDetail(anime.category, anime.id)
        navController.navigate(navAction)
    }
}
