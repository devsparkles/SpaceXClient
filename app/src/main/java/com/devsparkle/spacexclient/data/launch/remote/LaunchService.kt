package com.devsparkle.spacexclient.data.launch.remote

import com.devsparkle.spacexclient.data.launch.remote.model.LaunchDTO
import retrofit2.http.GET

interface LaunchService {

    @GET("/launches")
    suspend fun getLaunchesList(): List<LaunchDTO>?

}