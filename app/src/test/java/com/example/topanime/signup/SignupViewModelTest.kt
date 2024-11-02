package com.example.topanime.signup

import app.cash.turbine.test
import com.example.testshared.loginResult
import com.example.topanime.testRules.CoroutinesTestRule
import com.example.topanime.ui.common.INVALID_EMAIL
import com.example.topanime.ui.common.INVALID_PASSWORD
import com.example.topanime.ui.signup.SignupViewModel
import com.example.topanime.ui.signup.SignupViewModel.*
import com.example.topanime.usecases.SignupUseCase
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
class SignupViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var signupUseCase: SignupUseCase

    @Mock
    lateinit var validEmail: ValidEmail

    @Mock
    lateinit var validPassword: ValidPassword

    lateinit var signupViewModel: SignupViewModel

    private val email = "test@gmail.com"
    private val password = "12346578"
    private val loginResultFailed = loginResult.copy(success = false, errorMsg = "Error test login")

    @Before
    fun setUp() {
        signupViewModel = SignupViewModel(signupUseCase, validEmail, validPassword)
    }

    @Test
    fun `The sign up email is invalid`() =
        runTest {
            // GIVEN
            val invalidEmail = "test"
            whenever(validEmail(invalidEmail)).thenReturn(false)
            //WHEN
            signupViewModel.onSigninSelected(invalidEmail, password)
            // THEN
            signupViewModel.state.test {
                assertEquals(UiState(showError = INVALID_EMAIL), awaitItem())
                cancel()
            }
        }

    @Test
    fun `The sign up password is invalid`() =
        runTest {
            // GIVEN
            val invalidPassword = "1234"
            whenever(validEmail(email)).thenReturn(true)
            whenever(validPassword(invalidPassword)).thenReturn(false)
            //WHEN
            signupViewModel.onSigninSelected(email, invalidPassword)
            // THEN
            signupViewModel.state.test {
                assertEquals(UiState(showError = INVALID_PASSWORD), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Sign up attempt failed`() =
        runTest {
            // GIVEN
            whenever(validEmail(email)).thenReturn(true)
            whenever(validPassword(password)).thenReturn(true)
            whenever(signupUseCase(email, password)).thenReturn(loginResultFailed)
            //WHEN
            signupViewModel.onSigninSelected(email, password)
            // THEN
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
            // GIVEN
            whenever(validEmail(email)).thenReturn(true)
            whenever(validPassword(password)).thenReturn(true)
            whenever(signupUseCase(email, password)).thenReturn(loginResult)
            //WHEN
            signupViewModel.onSigninSelected(email, password)
            // THEN
            signupViewModel.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true), awaitItem())
                assertEquals(UiState(signin = true, showError = null), awaitItem())
                cancel()
            }
        }
}
