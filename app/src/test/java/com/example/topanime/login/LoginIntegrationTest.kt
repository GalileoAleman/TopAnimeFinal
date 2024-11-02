package com.example.topanime.login

import app.cash.turbine.test
import com.example.topanime.FakeFirebaseServerDataSource
import com.example.topanime.FakeValidator
import com.example.topanime.data.FirebaseRepository
import com.example.topanime.testRules.CoroutinesTestRule
import com.example.topanime.ui.common.INVALID_EMAIL
import com.example.topanime.ui.common.INVALID_PASSWORD
import com.example.topanime.ui.login.LoginViewModel
import com.example.topanime.ui.login.LoginViewModel.UiState
import com.example.topanime.usecases.LoginUseCase
import com.example.topanime.usecases.ValidEmail
import com.example.topanime.usecases.ValidPassword
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginIntegrationTest {
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
    fun `The login email is invalid`() =
        runTest {
            val loginViewModel = createLoginViewModel()

            loginViewModel.onLoginSelected("test", password)

            loginViewModel.state.test {
                assertEquals(UiState(showError = INVALID_EMAIL), awaitItem())
                cancel()
            }
        }

    @Test
    fun `The login password email is invalid`() =
        runTest {
            val loginViewModel = createLoginViewModel()

            loginViewModel.onLoginSelected(email, "1234")

            loginViewModel.state.test {
                assertEquals(UiState(showError = INVALID_PASSWORD), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Login attempt failed`() =
        runTest {
            val loginViewModel = createLoginViewModel()

            val emailFailed = "testFailed@gmail.com"

            loginViewModel.onLoginSelected(emailFailed, password)

            loginViewModel.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true), awaitItem())
                assertEquals(UiState(login = false, showError = "Error test login"), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Login is successful`() =
        runTest {
            val loginViewModel = createLoginViewModel()

            loginViewModel.onLoginSelected(email, password)

            loginViewModel.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true), awaitItem())
                assertEquals(UiState(login = true, showError = null), awaitItem())
                cancel()
            }
        }

    fun createLoginViewModel(): LoginViewModel {
        val firebaseRepository = FirebaseRepository(
            firebaseRemoteDataSource,
            validator
        )

        val loginUseCase = LoginUseCase(firebaseRepository)
        val validEmail = ValidEmail(firebaseRepository)
        val validPassword = ValidPassword(firebaseRepository)

        return LoginViewModel(loginUseCase, validEmail, validPassword)
    }
}