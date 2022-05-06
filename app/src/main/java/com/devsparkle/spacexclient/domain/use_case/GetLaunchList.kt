package com.devsparkle.spacexclient.domain.use_case

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.repository.LaunchRepository

class GetLaunchList(
    private val launchRepository: LaunchRepository
) {
    suspend operator fun invoke(): List<Launch> {
        return launchRepository.getAllLaunches()
    }
}
