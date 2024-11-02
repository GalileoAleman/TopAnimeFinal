package com.example.topanime.data

import com.example.topanime.data.datasource.ImageLocalDataSource
import javax.inject.Inject

class ImageRepository @Inject constructor(private val imageLocalDataSource: ImageLocalDataSource) {
    fun saveImage(image: String) = imageLocalDataSource.saveImage(image)

    fun getImage(): String = imageLocalDataSource.getImage()
}
