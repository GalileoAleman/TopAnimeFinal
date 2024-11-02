package com.example.topanime.data.server.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeRemote(
    val duration: String?,
    val episodes: Int?,
    val favorites: Int,
    val genres: List<Genre>,
    val images: Images,
    @SerializedName("mal_id")
    val id: Int,
    val popularity: Int,
    val rank: Int,
    val rating: String?,
    val score: Float,
    val season: String?,
    val source: String?,
    val status: String,
    val studios: List<Studio?>?,
    val synopsis: String,
    val title: String,
    val type: String,
    val url: String,
    val year: Int?
) : Parcelable
