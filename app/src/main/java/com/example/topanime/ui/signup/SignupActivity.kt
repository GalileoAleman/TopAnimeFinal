package com.example.topanime.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.topanime.databinding.ActivitySignupBinding
import com.example.topanime.ui.common.FirebaseAuthMsgError
import com.example.topanime.ui.common.hideKeyBoard
import com.example.topanime.ui.common.toast
import com.example.topanime.ui.common.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    companion object {
        const val TAG = "SignupActivity"
    }

    private val viewModel : SignupViewModel by viewModels()
    private lateinit var signupState : SignupState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signupState = SignupState(this)

        binding.signinButton.setOnClickListener{
            it.hideKeyBoard()
            viewModel.onSigninSelected(binding.signupEmail.text.toString(), binding.signupPassword.text.toString())
        }

        lifecycleScope.launch{
            viewModel.state.collect{
                binding.updateUi(it)
            }
        }
    }

    private fun ActivitySignupBinding.updateUi(state: SignupViewModel.UiState) {
        progress.visible = state.loading

        if (state.signin)
            signupState.navigateToMain()
        else
            applicationContext.toast(FirebaseAuthMsgError.authMsgError(state.showError)?.let { getString(it) }, Toast.LENGTH_LONG)
    }
}
