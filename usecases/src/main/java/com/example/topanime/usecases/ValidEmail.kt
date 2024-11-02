package com.example.topanime.usecases

import com.example.topanime.data.FirebaseRepository
import javax.inject.Inject

class ValidEmail @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    operator fun invoke(email : String) = firebaseRepository.isValidEmail(email)
}
