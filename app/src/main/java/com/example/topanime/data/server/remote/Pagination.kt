package com.example.topanime.data.server.remote


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Pagination(
    val has_next_page: Boolean,
    val items: Items,
    val last_visible_page: Int
) : Parcelable