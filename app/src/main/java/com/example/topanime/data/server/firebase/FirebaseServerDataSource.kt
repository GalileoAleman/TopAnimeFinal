package com.example.topanime.data.server.firebase

import com.example.topanime.data.datasource.firebase.FirebaseRemoteDataSource
import com.example.topanime.domain.LoginResult
import com.example.topanime.ui.common.AnimeSharedPreferences
import com.example.topanime.ui.common.ERROR_DEFAULT
import com.example.topanime.ui.common.getEmailName
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseServerDataSource @Inject constructor(private val firebaseAuth : FirebaseAuth, private val sharedPreferences: AnimeSharedPreferences) : FirebaseRemoteDataSource {

    override suspend fun login(email: String, password: String) = runCatching {
        sharedPreferences.saveString("Email", email.getEmailName())
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }.toLoginResult()

    override suspend fun signin(email: String, password: String) = runCatching {
        sharedPreferences.saveString("Email", email.getEmailName())
        sharedPreferences.saveString("Image", "")
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }.toLoginResult()

    override fun signout() = firebaseAuth.signOut()

    override suspend fun getCurrentUser(): Boolean = runCatching {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            try {
                currentUser.getIdToken(true).await()
                true
            } catch (e: FirebaseAuthInvalidUserException) {
                false
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }
    }.getOrElse {
        false
    }

    private fun Result<AuthResult>.toLoginResult(): LoginResult {
        return when {
            isSuccess -> {
                LoginResult(true, null)
            }
            else -> {
                val exception = exceptionOrNull()
                if (exception is FirebaseAuthException) {
                    LoginResult(false, exception.errorCode)
                }else
                    LoginResult(false, ERROR_DEFAULT)
            }
        }
    }
}
