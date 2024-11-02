package com.example.topanime.usecases

import com.example.topanime.data.AnimesRepository
import com.example.topanime.domain.Anime
import javax.inject.Inject

class SwitchAnimeFavUseCase @Inject constructor(private val animesRepository: AnimesRepository) {
    suspend operator fun invoke(anime : Anime) = animesRepository.switchFav(anime)
}
