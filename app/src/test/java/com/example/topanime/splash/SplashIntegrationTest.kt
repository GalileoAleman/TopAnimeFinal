package com.example.topanime.splash

import app.cash.turbine.test
import com.example.topanime.FakeFirebaseServerDataSource
import com.example.topanime.FakeValidator
import com.example.topanime.data.FirebaseRepository
import com.example.topanime.testRules.CoroutinesTestRule
import com.example.topanime.ui.signup.SignupViewModel
import com.example.topanime.ui.splash.SplashViewModel
import com.example.topanime.ui.splash.SplashViewModel.UiState
import com.example.topanime.usecases.CurrentUserUseCase
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
class SplashIntegrationTest {
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
    fun `Validates that the user is logged in to go to main`() =
        runTest {

            val signupViewModel = createSignupViewModel()
            signupViewModel.onSigninSelected(email, password)

            val splashViewModel = createSplashViewModel()

            splashViewModel.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(userLoged = true), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Validate that the user is not logged in to go to login`() =
        runTest {
            val splashViewModel = createSplashViewModel()

            splashViewModel.state.test {
                assertEquals(UiState(userLoged = false), awaitItem())
                cancel()
            }
        }

    fun createSplashViewModel(): SplashViewModel {
        val firebaseRepository = FirebaseRepository(
            firebaseRemoteDataSource,
            validator
        )

        val currentUserUseCase = CurrentUserUseCase(firebaseRepository)

        return SplashViewModel(currentUserUseCase)
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