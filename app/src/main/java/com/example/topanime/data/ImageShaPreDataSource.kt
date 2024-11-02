package com.example.topanime.data

import com.example.topanime.data.datasource.ImageLocalDataSource
import com.example.topanime.ui.common.AnimeSharedPreferences
import javax.inject.Inject

class ImageShaPreDataSource @Inject constructor(private val sharedPreferences: AnimeSharedPreferences) : ImageLocalDataSource {
    override fun saveImage(image: String) = sharedPreferences.saveString("Image", image)


    override fun getImage(): String = sharedPreferences.getString("Image")
}
