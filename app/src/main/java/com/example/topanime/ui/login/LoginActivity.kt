package com.example.topanime.ui.login

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.lifecycleScope
import com.example.topanime.R
import com.example.topanime.databinding.ActivityLoginBinding
import com.example.topanime.ui.common.hideKeyBoard
import com.example.topanime.ui.common.toast
import com.example.topanime.ui.common.visible
import com.example.topanime.ui.common.FirebaseAuthMsgError.Companion.authMsgError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    companion object {
        const val TAG = "LoginActivity"
        const val TO_MAIN = true
        const val TO_SIGNUP = false
    }

    private val viewModel : LoginViewModel by viewModels()
    private lateinit var loginState: LoginState

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginState = LoginState(this)

        binding.signupHereLogin.text = buildSpannedString {
            color(getColor(R.color.firstText), {append(getString(R.string.dont_have_an_account))})
            append(" ")
            color(getColor(R.color.secondary), {append(getString(R.string.sign_up_here))})
        }

        binding.signupHereLogin.setOnClickListener {
            it.hideKeyBoard()
            loginState.navigateTo(TO_SIGNUP)
        }

        binding.loginButton.setOnClickListener {
            it.hideKeyBoard()
            viewModel.onLoginSelected(binding.loginEmail.text.toString(), binding.loginPassword.text.toString())
        }

        lifecycleScope.launch{
            viewModel.state.collect{
                binding.updateUi(it)
            }
        }
    }

    private fun ActivityLoginBinding.updateUi(state: LoginViewModel.UiState) {
        progress.visible = state.loading

        if (state.login) {
            loginState.navigateTo(TO_MAIN)
            finish()
        }
        else
            applicationContext.toast(authMsgError(state.showError)?.let { getString(it) }, Toast.LENGTH_LONG)
    }
}
