package com.example.topanime.data.common

interface IValidator {
    fun isValidEmail(email: String): Boolean
    fun isValidPassword(password: String): Boolean
}
