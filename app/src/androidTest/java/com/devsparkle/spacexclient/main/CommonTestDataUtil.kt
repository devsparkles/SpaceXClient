package com.devsparkle.spacexclient.main

import com.devsparkle.spacexclient.data.launch.filter.Order
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object CommonTestDataUtil {

    fun dispatch(request: RecordedRequest): MockResponse? {

        if (request.method.equals("POST")) {
            return when (request.path) {

                "/v4/launches/query?page=0&size=5&launch_year=2022&launch_success=true&order=ASC" -> {
                    MockResponse().setResponseCode(200)
                        .setBody(
                            readFile("launch-sort-asc.json")
                        )
                }
                "/v4/launches/query?page=0&size=5&launch_year=2022&launch_success=true&order=DESC" -> {
                    MockResponse().setResponseCode(200)
                        .setBody(
                            readFile("launch-sort-desc.json")
                        )
                }


                else -> {
                    MockResponse().setResponseCode(404).setBody("{}")
                }
            }


        } else if (request.method.equals("GET")) {

            return when (request.path) {
                "/v4/rockets/5e9d0d95eda69955f709d1eb" -> {
                    MockResponse().setResponseCode(200)
                        .setBody( readFile("rocket-one.json"))
                }

                "/v4/launches" -> {
                    MockResponse().setResponseCode(200)
                        .setBody(
                            readFile("six_launches.json")
                        )
                }

                else -> {
                    MockResponse().setResponseCode(404).setBody("{}")
                }
            }


        } else {
            // 3
            return MockResponse().setResponseCode(401).setBody("{}")
        }


    }

    @Throws(IOException::class)
    fun readFile(jsonFileName: String): String {
        val inputStream = this::class.java
            .getResourceAsStream("/assets/$jsonFileName") ?: this::class.java
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