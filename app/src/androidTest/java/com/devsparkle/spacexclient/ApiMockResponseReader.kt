package com.devsparkle.spacexclient

import androidx.test.platform.app.InstrumentationRegistry

class ApiMockResponseReader(private val filename: String) {
    fun getContent(): String {
        return InstrumentationRegistry.getInstrumentation().context.assets.open(filename).readBytes().toString(Charsets.UTF_8)
    }
}