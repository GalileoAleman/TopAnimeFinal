package com.example.topanime.usecases

import com.example.topanime.data.AnimesRepository
import com.example.topanime.domain.Anime
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindAnimeUseCase @Inject constructor(private val animesRepository: AnimesRepository) {
    operator fun invoke(animeId: Long, animeCat: String): Flow<Anime>? = animesRepository.findById(animeId, animeCat)
}
