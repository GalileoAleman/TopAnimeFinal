package com.example.topanime.data

import com.example.topanime.data.datasource.firebase.FirebaseRemoteDataSource
import com.example.testshared.*
import com.example.topanime.data.common.IValidator
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import org.junit.Assert.*


@RunWith(MockitoJUnitRunner::class)
class FirebaseRepositoryTest {
    @Mock
    lateinit var firebaseRemoteDataSource: FirebaseRemoteDataSource

    @Mock
    lateinit var validator: IValidator

    lateinit var firebaseRepository: FirebaseRepository

    @Before
    fun setUp() {
        firebaseRepository = FirebaseRepository(
            firebaseRemoteDataSource,
            validator
        )
    }

    @Test
    fun `Validate that the user is logged in`() = runBlocking{
        val currentUser = true
        // GIVEN
        whenever(firebaseRemoteDataSource.getCurrentUser()).thenReturn(currentUser)
        // WHEN
        val result = firebaseRepository.getCurrentUser()
        // THEN
        assertEquals(currentUser, result)
    }

    @Test
    fun `Validate that the user is not logged in`() = runBlocking{
        val currentUser = false
        // GIVEN
        whenever(firebaseRemoteDataSource.getCurrentUser()).thenReturn(currentUser)
        // WHEN
        val result = firebaseRepository.getCurrentUser()
        // THEN
        assertEquals(currentUser, result)
    }

    @Test
    fun `Successful login with firebase`() = runBlocking{
        // GIVEN
        whenever(firebaseRemoteDataSource.login(userTest, passwordTest)).thenReturn(loginResult)
        // WHEN
        val result = firebaseRepository.login(userTest, passwordTest)
        // THEN
        assertEquals(result, loginResult)
    }

    @Test
    fun `Failed login with firebase`() = runBlocking{
        val loginResultF = loginResult.copy(success = false, errorMsg = "Failed login")
        // GIVEN
        whenever(firebaseRemoteDataSource.login(userTest, passwordTest)).thenReturn(loginResultF)
        // WHEN
        val result = firebaseRepository.login(userTest, passwordTest)
        // THEN
        assertEquals(result, loginResultF)
    }

    @Test
    fun `Successful registration with firebase`() = runBlocking{
        // GIVEN
        whenever(firebaseRemoteDataSource.signin(userTest, passwordTest)).thenReturn(loginResult)
        // WHEN
        val result = firebaseRepository.signin(userTest, passwordTest)
        // THEN
        assertEquals(result, loginResult)
    }

    @Test
    fun `Failed registration with firebase`() = runBlocking{
        // GIVEN
        whenever(firebaseRemoteDataSource.signin(userTest, passwordTest)).thenReturn(loginResult)
        // WHEN
        val result = firebaseRepository.signin(userTest, passwordTest)
        // THEN
        assertEquals(result, loginResult)
    }

    @Test
    fun `Validate email format`() {
        val email = "test@example.com"
        val isValid = true
        // GIVEN
        whenever(validator.isValidEmail(email)).thenReturn(isValid)
        // WHEN
        val result = firebaseRepository.isValidEmail(email)
        // THEN
        assertEquals(isValid, result)
    }

    @Test
    fun `Validate password format`() {
        val password = "Password123"
        val isValid = true
        // GIVEN
        whenever(validator.isValidPassword(password)).thenReturn(isValid)
        // WHEN
        val result = firebaseRepository.isValidPassword(password)
        // THEN
        assertEquals(isValid, result)
    }
}

