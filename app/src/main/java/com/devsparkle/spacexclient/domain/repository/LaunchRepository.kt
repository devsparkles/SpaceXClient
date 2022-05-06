package com.devsparkle.spacexclient.domain.repository

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.domain.model.Launch

interface LaunchRepository {
    suspend fun getAllLaunches(): List<Launch>
}