package com.devsparkle.spacexclient.domain.repository.remote

import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.model.paged.PageSummary

interface LaunchRepository {
    suspend fun getAllLaunches(): List<Launch>

    suspend fun filter(
        page: Int, size: Int,
        launchYear: Int,
        launchSuccess: Boolean,
        orderBy: String
    ): PageSummary<List<Launch>>?
}