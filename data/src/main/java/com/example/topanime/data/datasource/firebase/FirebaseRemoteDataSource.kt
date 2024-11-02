package com.example.topanime.data.datasource.firebase

import com.example.topanime.domain.LoginResult

interface FirebaseRemoteDataSource {
    suspend fun getCurrentUser(): Boolean
    suspend fun login(email: String, password: String): LoginResult
    suspend fun signin(email: String, password: String): LoginResult
    fun signout()
}