package com.example.topanime.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.topanime.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel : SplashViewModel by viewModels()
    private lateinit var splashState: SplashState

    companion object {
        const val TAG = "SplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        splashState = SplashState(this)

        lifecycleScope.launch {
            viewModel.state.collect{ state ->
                Log.d(TAG, "userLoged: " + state.userLoged)
                state.userLoged?.let {
                    splashState.navigateTo(it)
                    finish()
                }
            }
        }
    }
}
