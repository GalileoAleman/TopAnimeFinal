package com.example.topanime.ui.signup

import android.content.Context
import android.content.Intent
import com.example.topanime.ui.main.MainActivity

class SignupState(private val context: Context) {
    fun navigateToMain() =
        context.startActivity(
                Intent(context, MainActivity::class.java)
        )
}