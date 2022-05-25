package com.devsparkle.spacexclient.domain.use_case

import androidx.lifecycle.LiveData
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.repository.local.LaunchLocalRepository

class ObserveLaunches(
    private val launchLocalRepository: LaunchLocalRepository
) {
    operator fun invoke(): LiveData<List<Launch>> {
        return launchLocalRepository.observeAllLaunches()

    }
}
