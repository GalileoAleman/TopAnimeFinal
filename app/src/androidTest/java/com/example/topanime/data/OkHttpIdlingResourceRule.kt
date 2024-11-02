package com.example.topanime.data

import androidx.test.espresso.IdlingRegistry
import com.example.topanime.ui.detail.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import javax.inject.Inject

class OkHttpIdlingResourceRule @Inject constructor(
    private val okHttpClient: OkHttpClient
) : TestWatcher() {

    private val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(resource)
    }

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(resource)
    }
}