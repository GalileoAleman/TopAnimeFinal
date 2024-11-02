package com.example.topanime.signup

import app.cash.turbine.test
import com.example.topanime.FakeFirebaseServerDataSource
import com.example.topanime.FakeValidator
import com.example.topanime.data.FirebaseRepository
import com.example.topanime.testRules.CoroutinesTestRule
import com.example.topanime.ui.common.INVALID_EMAIL
import com.example.topanime.ui.common.INVALID_PASSWORD
import com.example.topanime.ui.signup.SignupViewModel
import com.example.topanime.ui.signup.SignupViewModel.UiState
import com.example.topanime.usecases.SignupUseCase
import com.example.topanime.usecases.ValidEmail
import com.example.topanime.usecases.ValidPassword
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignupIntegrationTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var firebaseRemoteDataSource: FakeFirebaseServerDataSource
    lateinit var validator: FakeValidator

    private val email = "test@gmail.com"
    private val password = "12346578"

    @Before
    fun setup() {
        firebaseRemoteDataSource = FakeFirebaseServerDataSource()
        validator = FakeValidator()
    }

    @Test
    fun `The sign up email is invalid`() =
        runTest {
            val signupViewModel = createSignupViewModel()

            signupViewModel.onSigninSelected("test", password)
            // THEN
            signupViewModel.state.test {
                assertEquals(UiState(showError = INVALID_EMAIL), awaitItem())
                cancel()
            }
        }

    @Test
    fun `The sign up password is invalid`() =
        runTest {
            val signupViewModel = createSignupViewModel()

            signupViewModel.onSigninSelected(email, "1324")

            signupViewModel.state.test {
                assertEquals(UiState(showError = INVALID_PASSWORD), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Sign up attempt failed`() =
        runTest {
            val signupViewModel = createSignupViewModel()

            val emailFailed = "testFailed@gmail.com"

            signupViewModel.onSigninSelected(emailFailed, password)

            signupViewModel.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true), awaitItem())
                assertEquals(UiState(signin = false, showError = "Error test login"), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Sign up is successful`() =
        runTest {
            val signupViewModel = createSignupViewModel()

            signupViewModel.onSigninSelected(email, password)

            signupViewModel.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true), awaitItem())
                assertEquals(UiState(signin = true, showError = null), awaitItem())
                cancel()
            }
        }

    fun createSignupViewModel(): SignupViewModel {
        val firebaseRepository = FirebaseRepository(
            firebaseRemoteDataSource,
            validator
        )

        val signupUseCase = SignupUseCase(firebaseRepository)
        val validEmail = ValidEmail(firebaseRepository)
        val validPassword = ValidPassword(firebaseRepository)

        return SignupViewModel(signupUseCase, validEmail, validPassword)
    }
}