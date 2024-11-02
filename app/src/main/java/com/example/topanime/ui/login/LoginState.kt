package com.example.topanime.ui.login

import android.content.Context
import android.content.Intent
import com.example.topanime.ui.main.MainActivity
import com.example.topanime.ui.signup.SignupActivity

class LoginState(private val context: Context) {
    fun navigateTo(userLoged : Boolean) =
        context.startActivity(
            if(userLoged)
                Intent(context, MainActivity::class.java)
            else
                Intent(context, SignupActivity::class.java)
        )
}
