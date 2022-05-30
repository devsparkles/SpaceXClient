package com.devsparkle.spacexclient

import com.devsparkle.spacexclient.main.CommonTestDataUtil
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

/**
 * Handles Requests from mock web server
 */
internal class SpaceXRequestDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        if (request.method.equals("POST")) {
            return when (request.path) {

                "/v4/launches/query?page=0&size=5&launch_year=2022&launch_success=true&order=ASC" -> {
                    MockResponse().setResponseCode(200)
                        .setBody(
                            CommonTestDataUtil.readFile("launch-sort-asc.json")
                        )
                }
                "/v4/launches/query?page=0&size=5&launch_year=2022&launch_success=true&order=DESC" -> {
                    MockResponse().setResponseCode(200)
                        .setBody(
                            CommonTestDataUtil.readFile("launch-sort-desc.json")
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
                        .setBody(CommonTestDataUtil.readFile("rocket-one.json"))
                }

                "/v4/launches" -> {
                    MockResponse().setResponseCode(200)
                        .setBody(
                            CommonTestDataUtil.readFile("six_launches.json")
                        )
                }
                "/v4/company" -> {
                    MockResponse().setResponseCode(200)
                        .setBody(
                            CommonTestDataUtil.readFile("company.json")
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

}