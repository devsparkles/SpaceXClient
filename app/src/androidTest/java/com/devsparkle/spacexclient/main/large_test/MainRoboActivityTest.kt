package com.devsparkle.spacexclient.main.large_test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devsparkle.spacexclient.main.MainActivity
import com.devsparkle.spacexclient.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainRoboActivityTest {
    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAssertHelloText() {
        onView(withId(R.id.action_filter)).check(ViewAssertions.matches(withText("FILTER")))
    }
}