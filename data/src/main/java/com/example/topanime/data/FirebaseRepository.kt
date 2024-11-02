package com.example.topanime.data

import com.example.topanime.data.common.IValidator
import com.example.topanime.data.datasource.firebase.FirebaseRemoteDataSource
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebaseRemoteDataSource: FirebaseRemoteDataSource, private val validator : IValidator) {

    suspend fun getCurrentUser(): Boolean = firebaseRemoteDataSource.getCurrentUser()

    suspend fun login(email: String, password: String) = firebaseRemoteDataSource.login(email, password)

    suspend fun signin(email: String, password: String) = firebaseRemoteDataSource.signin(email, password)

    fun signout() = firebaseRemoteDataSource.signout()

    fun isValidEmail(email: String) = validator.isValidEmail(email)

    fun isValidPassword(password: String) = validator.isValidPassword(password)
}
