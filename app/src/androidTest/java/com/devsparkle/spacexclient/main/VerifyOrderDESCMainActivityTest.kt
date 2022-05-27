package com.devsparkle.spacexclient.main

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.devsparkle.spacexclient.R
import com.devsparkle.spacexclient.base.di.SPACE_X_URL
import com.devsparkle.spacexclient.base.di.baseModule
import com.devsparkle.spacexclient.base.di.urlsModule
import com.devsparkle.spacexclient.data.di.localDataModule
import com.devsparkle.spacexclient.data.di.remoteDataModule
import com.devsparkle.spacexclient.domain.di.domainModule
import com.devsparkle.spacexclient.main.di.mainModule
import com.devsparkle.spacexclient.utils.IdlingEntity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTest
import java.util.concurrent.TimeUnit

@LargeTest
@RunWith(AndroidJUnit4::class)
class VerifyOrderDESCMainActivityTest : KoinTest {

    private val idlingResource = SimpleIdlingResource()

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    companion object {
        val server = MockWebServer()
        val dispatcher: Dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(
                request: RecordedRequest
            ): MockResponse {
                return CommonTestDataUtil.dispatch(request) ?: MockResponse().setResponseCode(404)
            }
        }

        @BeforeClass
        @JvmStatic
        fun setup() {
            server.dispatcher = dispatcher
            server.start()
        }

        private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int
        ): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("Child at position $position in parent ")
                    parentMatcher.describeTo(description)
                }

                public override fun matchesSafely(view: View): Boolean {
                    val parent = view.parent
                    return (parent is ViewGroup && parentMatcher.matches(parent)
                            && view == parent.getChildAt(position))
                }
            }
        }

    }

    // will override the default server url with the mockserver url
    private fun loadKoinTestModules(serverUrl: String) {
        loadKoinModules(listOf(module(override = true) {
            single(named(SPACE_X_URL)) { serverUrl }
        }))
    }

    private fun loadKoinTestModulesTryCatch(serverUrl: String) {
        try {
            loadKoinTestModules(serverUrl)
        } catch (koinAlreadyStartedException: KoinAppAlreadyStartedException) {
            stopKoin()
            loadKoinTestModules(serverUrl)
            loadKoinModules(listOf(module(override = true) {
                single(named(SPACE_X_URL)) { serverUrl }
            }))
        } catch (illegalStateException: IllegalStateException) {
            startKoin {
                modules(
                    listOf(
                        urlsModule,
                        // shared module
                        baseModule,
                        // data remote and local module
                        localDataModule,
                        remoteDataModule,
                        // dto objects and use cases
                        domainModule,
                        // domain modules
                        mainModule
                    )
                )
            }
            loadKoinTestModules(serverUrl)
        }
    }

    @Subscribe
    fun onEvent(idlingEntity: IdlingEntity) {
        idlingResource.incrementBy(idlingEntity.incrementValue)
    }

    @Before
    fun beforeTestsRun() {
        val serverUrl = server.url("").toString()
        loadKoinTestModulesTryCatch(serverUrl)

        EventBus.getDefault().register(this)
        IdlingRegistry.getInstance().register(idlingResource)
    }


    @Test
    fun verifyOrderDESCMainActivityTest() {
        val actionMenuItemView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.action_filter),
                ViewMatchers.withContentDescription("FILTER"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        actionMenuItemView.perform(ViewActions.click())
        val materialRadioButton = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.radio_desc),
                ViewMatchers.withText("Launch : most recent to oldest"),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.radiogroup_sort_by),
                        childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("androidx.appcompat.widget.LinearLayoutCompat")),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialRadioButton.perform(ViewActions.click())
        val actionMenuItemView2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.action_save), ViewMatchers.withText("SAVE"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.toolbar),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        actionMenuItemView2.perform(ViewActions.click())

        // wait for loading

        TimeUnit.SECONDS.sleep(20);

        val textView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.tv_date_time_value),
                ViewMatchers.withText("24/03/2006 at 22:30:00"),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.cl_launch_value),
                        ViewMatchers.withParent(
                            IsInstanceOf.instanceOf(
                                ViewGroup::class.java
                            )
                        )
                    )
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText("24/03/2006 at 22:30:00")))
    }

    @After
    fun afterTestsRun() {
        // eventbus and idling resources unregister.
        IdlingRegistry.getInstance().unregister(idlingResource)
        EventBus.getDefault().unregister(this)
        stopKoin()
    }

}