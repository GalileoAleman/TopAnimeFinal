package com.example.topanime.data.server.remote


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Jpg(
    val image_url: String,
    val large_image_url: String,
    val small_image_url: String
) : Parcelable