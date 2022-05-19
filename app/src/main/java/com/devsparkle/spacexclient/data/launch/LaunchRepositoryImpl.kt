package com.devsparkle.spacexclient.data.launch

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.data.launch.remote.LaunchService
import com.devsparkle.spacexclient.data.rocket.remote.RocketService
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.model.Rocket
import com.devsparkle.spacexclient.domain.repository.LaunchRepository

class LaunchRepositoryImpl(
    private val launchService: LaunchService,
    private val rocketService: RocketService
) : LaunchRepository {

    override suspend fun getAllLaunches(): List<Launch> {
        val launchResponse = Resource.of {
            launchService.getLaunchesList()
        }

        val launches = mutableListOf<Launch>()
        if (launchResponse.isNotAnError()) {
            launchResponse.value()?.forEach { launch ->
                val rocket = Rocket(name = "", type = "")
                launch.rocketId?.let { rocketid ->
                    val rocketResponse = Resource.of {
                        rocketService.getRocketById(rocketid)
                    }
                    if (rocketResponse.isNotAnError()) {
                        rocketResponse.value()?.let {
                            rocket.name = it.name
                            rocket.type = it.type
                        }
                    }
                }

                launches.add(
                    Launch(
                        missionPatchSmallImageUrl = launch.links?.patch?.small,
                        missionPatchLargeImageUrl = launch.links?.patch?.large,
                        missionName = launch.name,
                        dateUtc = launch.dateUtc,
                        dateLocal= launch.dateLocal,
                        rocket,
                        success = launch.success,
                    )
                )

            }
        }

        return launches
    }
}