package com.devsparkle.spacexclient

import android.view.View
import android.view.ViewGroup
import com.devsparkle.spacexclient.base.local.SpaceXDatabase
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest

open class BaseUiTest : KoinTest {

    protected lateinit var webServer: MockWebServer

    @Before
    @Throws(Exception::class)
    fun server() {
        webServer = MockWebServer()
        webServer.start(ApiTestRunner.MOCK_WEB_SERVER_PORT)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        webServer.shutdown()
        val db = getKoin().get<SpaceXDatabase>()
        db.clearAllTables()
    }
}