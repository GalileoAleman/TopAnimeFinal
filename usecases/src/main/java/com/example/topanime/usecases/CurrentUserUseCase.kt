package com.example.topanime.usecases

import com.example.topanime.data.FirebaseRepository
import javax.inject.Inject

class CurrentUserUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(): Boolean = firebaseRepository.getCurrentUser()
}
