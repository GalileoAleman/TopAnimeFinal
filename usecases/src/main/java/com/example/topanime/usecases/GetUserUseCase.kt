package com.example.topanime.usecases

import com.example.topanime.data.UserRepository
import javax.inject.Inject

class GetUserUseCase  @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): String = userRepository.getUserName()
}
