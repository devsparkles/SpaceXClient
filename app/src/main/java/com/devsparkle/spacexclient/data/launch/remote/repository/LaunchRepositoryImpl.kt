package com.devsparkle.spacexclient.data.launch.remote.repository

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.data.launch.mapper.toDomain
import com.devsparkle.spacexclient.data.launch.remote.LaunchService
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.model.Rocket
import com.devsparkle.spacexclient.domain.model.paged.PageSummary
import com.devsparkle.spacexclient.domain.repository.remote.LaunchRepository
import timber.log.Timber

class LaunchRepositoryImpl(
    private val launchService: LaunchService
) : LaunchRepository {

    override suspend fun getAllLaunches(): List<Launch> {
        val launchResponse = Resource.of {
            launchService.getLaunchesList()
        }

        val launches = mutableListOf<Launch>()
        if (launchResponse.isNotAnError()) {
            launchResponse.value()?.forEach { launch ->
                val rocket = Rocket(rocketId = "", name = "", type = "")
                launch.rocketId?.let { id ->
                    rocket.rocketId = id
                }
                launches.add(launch.toDomain())
            }
        }

        return launches
    }

    override suspend fun filter(
        page: Int, size: Int,
        launchYear: String?,
        launchSuccess: Boolean?,
        orderBy: String?
    ): PageSummary<List<Launch>>? {
        try {
            val result = launchService.filter(
                page = page,
                launchYear = launchYear,
                launchSuccess = launchSuccess,
                order = orderBy,
                size = size
            )
            return result.toDomain()
        } catch (e: Exception) {
            Timber.e(e, "Error fetching filter")
        }
        return null
    }
}