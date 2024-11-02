package com.example.topanime.ui.main

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.topanime.R
import com.example.topanime.databinding.ActivityMainBinding
import com.example.topanime.ui.common.AndroidPermissionChecker
import com.example.topanime.ui.common.PermissionRequester
import com.example.topanime.ui.common.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mainState: MainState

    @Inject
    lateinit var androidPermissionChecker: AndroidPermissionChecker

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            mainState.saveImage(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding   = ActivityMainBinding.inflate(layoutInflater)
        val drawerLayout = binding.mainDrawerLayout
        val navView = binding.navigationView
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        mainState = MainState(this, drawerLayout, navView, mainViewModel, lifecycleScope,
            androidPermissionChecker, PermissionRequester(this,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    Manifest.permission.READ_MEDIA_IMAGES
                else Manifest.permission.READ_EXTERNAL_STORAGE),
            pickImageLauncher)

        mainState.setupNavigation(toggle)

        val userImgView = navView.getHeaderView(0).findViewById<ImageView>(R.id.user_img)

        val userNameView = navView.getHeaderView(0).findViewById<TextView>(R.id.user_name)

        userImgView.setOnClickListener {
            mainState.requestGalleryPermission { isGranted ->
                if (isGranted) {
                    mainState.openGallery()
                }
                else
                    applicationContext.toast(getString(R.string.storage_permission_to_select_an_image))
            }
        }

        lifecycleScope.launch {
            mainViewModel.state.collect{
                if(it.signOut) {
                    mainState.navigateToLogin()
                    finish()
                }

                it.image?.let { img -> Glide.with(this@MainActivity)
                    .load(img)
                    .into(userImgView)
                }

                it.userName?.let { name -> userNameView.text = name }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}
