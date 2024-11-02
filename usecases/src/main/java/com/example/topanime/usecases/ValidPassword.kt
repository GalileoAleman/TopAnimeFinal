package com.example.topanime.usecases

import com.example.topanime.data.FirebaseRepository
import javax.inject.Inject

class ValidPassword @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    operator fun invoke(password : String) = firebaseRepository.isValidPassword(password)
}
