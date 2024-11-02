package com.example.topanime.data

import com.example.topanime.data.common.IValidator
import com.example.topanime.ui.common.isValidEmail
import com.example.topanime.ui.common.isValidPassword
import javax.inject.Inject

class Validator @Inject constructor() : IValidator {
    override fun isValidEmail(email: String) = email.isValidEmail()

    override fun isValidPassword(password: String) = password.isValidPassword()
}
