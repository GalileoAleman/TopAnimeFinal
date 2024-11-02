package com.example.topanime.login

import app.cash.turbine.test
import com.example.testshared.loginResult
import com.example.topanime.testRules.CoroutinesTestRule
import com.example.topanime.ui.common.INVALID_EMAIL
import com.example.topanime.ui.common.INVALID_PASSWORD
import com.example.topanime.ui.login.LoginViewModel
import com.example.topanime.ui.login.LoginViewModel.*
import com.example.topanime.usecases.LoginUseCase
import com.example.topanime.usecases.ValidEmail
import com.example.topanime.usecases.ValidPassword
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var loginUseCase: LoginUseCase

    @Mock
    lateinit var validEmail: ValidEmail

    @Mock
    lateinit var validPassword: ValidPassword

    lateinit var loginViewModel: LoginViewModel

    private val email = "test@gmail.com"
    private val password = "12346578"
    private val loginResultFailed = loginResult.copy(success = false, errorMsg = "Error test login")

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(loginUseCase, validEmail, validPassword)
    }

    @Test
    fun `The login email is invalid`() =
        runTest {
            // GIVEN
            val invalidEmail = "test"
            whenever(validEmail(invalidEmail)).thenReturn(false)
            //WHEN
            loginViewModel.onLoginSelected(invalidEmail, password)
            // THEN
            loginViewModel.state.test {
                assertEquals(UiState(showError = INVALID_EMAIL), awaitItem())
                cancel()
            }
        }

    @Test
    fun `The login password email is invalid`() =
        runTest {
            // GIVEN
            val invalidPassword = "1234"
            whenever(validEmail(email)).thenReturn(true)
            whenever(validPassword(invalidPassword)).thenReturn(false)
            //WHEN
            loginViewModel.onLoginSelected(email, invalidPassword)
            // THEN
            loginViewModel.state.test {
                assertEquals(UiState(showError = INVALID_PASSWORD), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Login attempt failed`() =
        runTest {
            // GIVEN
            whenever(validEmail(email)).thenReturn(true)
            whenever(validPassword(password)).thenReturn(true)
            whenever(loginUseCase(email, password)).thenReturn(loginResultFailed)
            //WHEN
            loginViewModel.onLoginSelected(email, password)
            // THEN
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
            // GIVEN
            whenever(validEmail(email)).thenReturn(true)
            whenever(validPassword(password)).thenReturn(true)
            whenever(loginUseCase(email, password)).thenReturn(loginResult)
            //WHEN
            loginViewModel.onLoginSelected(email, password)
            // THEN
            loginViewModel.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true), awaitItem())
                assertEquals(UiState(login = true, showError = null), awaitItem())
                cancel()
            }
        }
}