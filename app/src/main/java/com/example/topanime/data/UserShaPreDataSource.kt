package com.example.topanime.data

import com.example.topanime.data.datasource.UserLocalDataSource
import com.example.topanime.ui.common.AnimeSharedPreferences
import javax.inject.Inject

class UserShaPreDataSource @Inject constructor(private val sharedPreferences: AnimeSharedPreferences) : UserLocalDataSource {

    override fun getUserName(): String = sharedPreferences.getString("Email")
}
