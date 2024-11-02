package com.example.topanime.ui.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.topanime.data.MockWebServerRule
import com.example.topanime.data.fromJson
import com.example.topanime.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import com.example.topanime.R
import okhttp3.OkHttpClient
import org.hamcrest.Matcher
import org.junit.After


@ExperimentalCoroutinesApi
@HiltAndroidTest
class AnimeDetailInstrumentationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    lateinit var resource: IdlingResource

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("seasonsNow.json")
        )

        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("topAnime.json")
        )

        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("topManga.json")
        )

        hiltRule.inject()

        resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(resource)
    }

    @Test
    fun click_a_anime_navigates_to_detail() {
        onView(isRoot()).perform(waitFor(2000))
        onView(withId(R.id.recycler_categories))
            .check(matches(isDisplayed()))
            .check(matches(hasMinimumChildCount(1)))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    fun waitFor(millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for $millis milliseconds"
            }

            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(millis)
            }
        }
    }
}