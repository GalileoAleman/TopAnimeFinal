package com.example.topanime.data

import com.example.topanime.data.datasource.UserLocalDataSource
import javax.inject.Inject

class UserRepository @Inject constructor(private val userLocalDataSource: UserLocalDataSource) {
    fun getUserName(): String = userLocalDataSource.getUserName()
}
