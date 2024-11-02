package com.example.topanime.data.server.remote


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
@Parcelize
data class Genre(
    val name: String,
    val type: String,
) : Parcelable