package com.example.topanime.ui.common

import android.app.Application
import androidx.room.Room
import com.example.topanime.data.database.AnimeDataBase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
