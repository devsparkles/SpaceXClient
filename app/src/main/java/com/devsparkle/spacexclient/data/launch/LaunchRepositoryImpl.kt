package com.devsparkle.spacexclient.data.launch

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.data.launch.remote.LaunchService
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.repository.LaunchRepository

class LaunchRepositoryImpl(private val launchService: LaunchService): LaunchRepository {

    override fun getAllLaunches(): Resource<List<Launch>> {
        TODO("Not yet implemented")
    }
}