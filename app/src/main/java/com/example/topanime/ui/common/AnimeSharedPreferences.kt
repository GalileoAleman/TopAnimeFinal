package com.example.topanime.ui.common

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AnimeSharedPreferences @Inject constructor(@ApplicationContext private val context: Context){
    companion object {
        private const val PREF_NAME = "TopAnimePrefs"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(value: String): String {
        return sharedPreferences.getString(value, "").toString()
    }
}
