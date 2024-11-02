package com.example.topanime.ui.splash

import android.content.Context
import android.content.Intent
import com.example.topanime.ui.login.LoginActivity
import com.example.topanime.ui.main.MainActivity

class SplashState(private val context: Context) {

    fun navigateTo(userLoged : Boolean) =
        context.startActivity(
            if(userLoged)
                Intent(context, MainActivity::class.java)
        else
            Intent(context, LoginActivity::class.java))
}
