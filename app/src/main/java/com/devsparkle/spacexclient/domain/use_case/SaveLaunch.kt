package com.devsparkle.spacexclient.domain.use_case

import com.devsparkle.spacexclient.data.launch.entities.LaunchEntity
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.repository.local.LaunchLocalRepository


class SaveLaunch(
    private val launchLocalRepository: LaunchLocalRepository
) {
    suspend operator fun invoke(l: Launch) {
        return launchLocalRepository.saveLaunch(l)
    }
}
