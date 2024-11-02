package com.example.topanime.ui.common

import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import javax.inject.Inject


class AndroidPermissionChecker @Inject constructor(private val application: Application) {
        fun check(permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            permission
        ) == PackageManager.PERMISSION_GRANTED
}
