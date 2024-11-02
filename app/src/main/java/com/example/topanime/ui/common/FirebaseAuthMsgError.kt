package com.example.topanime.ui.common

import com.example.topanime.R

sealed class FirebaseAuthMsgError {
    companion object {
        val authMsgError: Map<String, Int> = mapOf(
            "ERROR_EMAIL_ALREADY_IN_USE" to R.string.error_email_already_in_use,
            "ERROR_INVALID_CREDENTIAL" to R.string.error_invalid_credential,
            "ERROR_INVALID_EMAIL" to R.string.error_invalid_email,
            "ERROR_WRONG_PASSWORD" to R.string.error_wrong_password,
            "ERROR_CREDENTIAL_ALREADY_IN_USE" to R.string.error_credential_already_in_use,
            "ERROR_USER_NOT_FOUND" to R.string.error_user_not_found,
            "ERROR_WEAK_PASSWORD" to R.string.error_weak_password,
            "ERROR_MISSING_EMAIL" to R.string.error_missing_email,
            "ERROR_DEFAULT" to R.string.an_authentication_error_occurred,
            "INVALID_EMAIL" to R.string.enter_valid_email_address,
            "INVALID_PASSWORD" to R.string.enter_valid_password
        )

        fun authMsgError(error: String?): Int? =  error?.let { authMsgError[it] }
    }
}
