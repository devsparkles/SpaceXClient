package com.devsparkle.spacexclient.domain.use_case

import com.devsparkle.spacexclient.domain.model.Rocket
import com.devsparkle.spacexclient.domain.repository.remote.RocketRepository

class GetRocketById(
    private val rocketRepository: RocketRepository
) {
    suspend operator fun invoke(id: String): Rocket {
        return rocketRepository.getRocketById(id)
    }
}
