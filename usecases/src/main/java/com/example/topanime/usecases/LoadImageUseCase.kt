package com.example.topanime.usecases

import com.example.topanime.data.ImageRepository
import javax.inject.Inject

class LoadImageUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    operator fun invoke(): String = imageRepository.getImage()
}
