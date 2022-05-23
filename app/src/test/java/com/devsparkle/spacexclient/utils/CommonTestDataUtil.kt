package com.devsparkle.spacexclient.utils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object CommonTestDataUtil {

    fun nonInterceptedDispatch(
        request: RecordedRequest
    ): MockResponse? {
        val headers = request.headers
        return when (request.path) {
            "/v4/launches" -> {
                MockResponse().setResponseCode(200)
                    .setBody(
                    readFile("six_launches.json")
                )
            }
            "/v4/rockets/5e9d0d95eda69955f709d1eb" -> {
                MockResponse().setResponseCode(200)
                    .setBody( readFile("rocket-one.json"))
            }
            else -> {
                MockResponse().setResponseCode(404).setBody("{}")
            }
        }
    }

    @Throws(IOException::class)
    fun readFile(jsonFileName: String): String {
        val inputStream = this::class.java
            .getResourceAsStream("/assets/$jsonFileName") ?:
        this::class.java
            .getResourceAsStream("/$jsonFileName")
        ?: throw NullPointerException(
            "Have you added the local resource correctly?, "
                    + "Hint: name it as: " + jsonFileName
        )
        val stringBuilder = StringBuilder()
        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var character: Int = bufferedReader.read()
            while (character != -1) {
                stringBuilder.append(character.toChar())
                character = bufferedReader.read()
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        } finally {
            inputStream.close()
            inputStreamReader?.close()
        }
        return stringBuilder.toString()
    }

}