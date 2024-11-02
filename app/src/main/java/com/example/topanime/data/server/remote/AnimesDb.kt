package com.example.topanime.data.server.remote


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
@Parcelize
data class AnimesDb(
    val `data`: List<AnimeRemote>,
    val pagination: Pagination
) : Parcelable