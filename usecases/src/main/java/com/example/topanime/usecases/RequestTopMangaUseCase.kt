package com.example.topanime.usecases

import com.example.topanime.data.AnimesRepository
import com.example.topanime.domain.AnimeListResult
import javax.inject.Inject

class RequestTopMangaUseCase @Inject constructor(private val animesRepository: AnimesRepository) {
    suspend operator fun invoke(): AnimeListResult = animesRepository.getTopsMangas()
}