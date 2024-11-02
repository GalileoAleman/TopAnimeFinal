package com.example.topanime.usecases

import com.example.topanime.data.FirebaseRepository
import com.example.topanime.domain.LoginResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(email : String, password : String): LoginResult = firebaseRepository.login(email, password)
}
