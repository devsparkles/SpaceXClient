package com.devsparkle.spacexclient.data.rocket.remote

import com.devsparkle.spacexclient.data.rocket.remote.model.RocketDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface RocketService {

    @GET("/v4/rockets/{id}")
    suspend fun getRocketById(@Path("id") rocketId: String): RocketDTO?

}