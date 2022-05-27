package com.devsparkle.spacexclient.data.launch.remote

import com.devsparkle.spacexclient.data.launch.remote.model.LaunchDTO
import com.devsparkle.spacexclient.data.launch.remote.model.PageSummaryDTO
import com.devsparkle.spacexclient.domain.model.paged.PageSummary
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LaunchService {

    @GET("/v4/launches")
    suspend fun getLaunchesList(): List<LaunchDTO>?

    @POST("/v4/launches/query")
    suspend fun filter(@Query("page") page: Int,
                       @Query("size") size: Int,
                       @Query("launch_year") launchYear: String?,
                       @Query("launch_success") launchSuccess: Boolean?,
                       @Query("order") order: String?): PageSummaryDTO<List<LaunchDTO>?>




}
