package com.example.topanime.usecases

import com.example.topanime.data.ImageRepository
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    operator fun invoke(image: String) = imageRepository.saveImage(image)
}
