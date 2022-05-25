package com.devsparkle.spacexclient.data.rocket.remote.repository

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.data.rocket.mapper.toDomain
import com.devsparkle.spacexclient.data.rocket.remote.RocketService
import com.devsparkle.spacexclient.domain.model.Rocket
import com.devsparkle.spacexclient.domain.repository.remote.RocketRepository

class RocketRepositoryImpl(
    private val rocketService: RocketService
) : RocketRepository {


    override suspend fun getRocketById(id: String): Rocket {
        val rocketResponse = Resource.of {
            rocketService.getRocketById(id)
        }
        if (rocketResponse.isNotAnError()) {
            return rocketResponse.value().toDomain()
        }

        return Rocket(rocketId = id, "", "")
    }
}