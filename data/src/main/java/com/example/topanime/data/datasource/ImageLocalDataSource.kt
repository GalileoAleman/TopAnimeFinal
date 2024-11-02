package com.example.topanime.data.datasource

interface ImageLocalDataSource {
    fun saveImage(image: String)
    fun getImage(): String
}
