package com.example.topanime.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.topanime.R
import com.example.topanime.ui.common.AndroidPermissionChecker
import com.example.topanime.ui.common.PermissionRequester
import com.example.topanime.ui.login.LoginActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainState(
    private val context: Context,
    private val drawerLayout: DrawerLayout,
    private val navView: NavigationView,
    private val viewModel: MainViewModel,
    private val scope: CoroutineScope,
    private val androidPermissionChecker: AndroidPermissionChecker,
    private val galleryPermissionRequester: PermissionRequester,
    private val pickImageLauncher: ActivityResultLauncher<String>
) {

    fun setupNavigation(toggle: ActionBarDrawerToggle) {
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            handleMenuSelection(menuItem)
            drawerLayout.closeDrawer(navView)
            true
        }
    }

    private fun handleMenuSelection(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_logout -> {
                viewModel.signOut()
            }
        }
    }

    fun navigateToLogin() =
        context.startActivity(
            Intent(context, LoginActivity::class.java)
        )

    fun requestGalleryPermission(afterRequest: (Boolean) -> Unit) {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else Manifest.permission.READ_EXTERNAL_STORAGE

        if (androidPermissionChecker.check(permission)) {
            afterRequest(true)
        } else {
            scope.launch {
                val result = galleryPermissionRequester.request()
                afterRequest(result)
            }
        }
    }

    fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    fun saveImage(img: Uri) {
        viewModel.saveImage(img)
    }
}
