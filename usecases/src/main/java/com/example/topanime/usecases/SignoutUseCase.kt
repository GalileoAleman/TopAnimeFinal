package com.example.topanime.usecases

import com.example.topanime.data.FirebaseRepository
import com.example.topanime.domain.LoginResult
import javax.inject.Inject

class SignoutUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    operator fun invoke(): Unit = firebaseRepository.signout()
}
