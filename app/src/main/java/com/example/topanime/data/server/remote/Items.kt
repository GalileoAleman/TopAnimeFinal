package com.example.topanime.data.server.remote


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Items(
    val count: Int,
    val per_page: Int,
    val total: Int
) : Parcelable