package com.example.topanime.splash

import app.cash.turbine.test

import com.example.topanime.testRules.CoroutinesTestRule
import com.example.topanime.ui.splash.SplashViewModel
import com.example.topanime.ui.splash.SplashViewModel.*
import com.example.topanime.usecases.CurrentUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var currentUserUseCase: CurrentUserUseCase

    lateinit var splashViewModel: SplashViewModel

    @Test
    fun `Validates that the user is logged in to go to main`() =
        runTest {
            // GIVEN
            whenever(currentUserUseCase()).thenReturn(true)
            //WHEN
            splashViewModel = SplashViewModel(currentUserUseCase)
            // THEN
            splashViewModel.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(userLoged = true), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Validate that the user is not logged in to go to login`() =
        runTest {
            // GIVEN
            whenever(currentUserUseCase()).thenReturn(false)
            //WHEN
            splashViewModel = SplashViewModel(currentUserUseCase)
            // THEN
            splashViewModel.state.test {
                assertEquals(UiState(userLoged = false), awaitItem())
                cancel()
            }
        }
}
