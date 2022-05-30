package com.devsparkle.spacexclient.main.large_test

import androidx.fragment.app.FragmentFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devsparkle.spacexclient.BaseTest
import com.devsparkle.spacexclient.main.MainActivity
import com.devsparkle.spacexclient.R
import com.devsparkle.spacexclient.main.SimpleIdlingResource
import com.devsparkle.spacexclient.main.VerifyOrderDESCMainActivityTest.Companion.server
import com.devsparkle.spacexclient.utils.IdlingEntity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


@RunWith(AndroidJUnit4::class)
class MainRoboActivityTest : BaseTest() {

//    @get:Rule
//    val rule = ActivityScenarioRule(MainActivity::class.java)
//

    lateinit var testScenario: ActivityScenario<MainActivity>
    private val idlingResource = SimpleIdlingResource()


    @Subscribe
    fun onEvent(idlingEntity: IdlingEntity) {
        idlingResource.incrementBy(idlingEntity.incrementValue)
    }


    @Before
    fun beforeTestsRun() {
        testScenario = ActivityScenario.launch(MainActivity::class.java)

        EventBus.getDefault().register(this)
        IdlingRegistry.getInstance().register(idlingResource)
    }
    @Test
    fun testAssertHelloText() {


        Thread.sleep(4_000)
    //    onView(withId(R.id.action_filter)).check(ViewAssertions.matches(withText("FILTER")))
    }

    @After
    fun afterTestsRun() {
        // eventbus and idling resources unregister.
        IdlingRegistry.getInstance().unregister(idlingResource)
        EventBus.getDefault().unregister(this)
        stopKoin()
        testScenario.close()
    }
}