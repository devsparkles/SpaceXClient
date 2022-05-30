package com.devsparkle.spacexclient

import androidx.test.espresso.IdlingRegistry
import com.devsparkle.spacexclient.utils.EspressoIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest

open class BaseTest : KoinTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    open fun setup() {
        mockWebServer = MockWebServer().apply {
            dispatcher = SpaceXRequestDispatcher()
            start(8080)
        }
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    open fun tearDown() {
        mockWebServer.shutdown()
         IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}