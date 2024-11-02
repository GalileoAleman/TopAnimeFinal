package com.example.topanime.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_anime")
data class TopAnime(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category : String,
    val duration: String?,
    val episodes: Int?,
    val favorites: Int,
    val popularity: Int,
    val rank: Int,
    val rating: String?,
    val score: Float,
    val season: String?,
    val source: String?,
    val status: String,
    val synopsis: String,
    val title: String,
    val type: String,
    val url: String,
    val year: Int?,
    val image: String,
    val genre: String?,
    val studios: String?,
    val favorite: Boolean
)
