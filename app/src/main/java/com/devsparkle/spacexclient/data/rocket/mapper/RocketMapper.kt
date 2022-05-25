package com.devsparkle.spacexclient.data.rocket.mapper

import com.devsparkle.spacexclient.data.rocket.remote.model.RocketDTO
import com.devsparkle.spacexclient.domain.model.Rocket

fun RocketDTO?.toDomain(): Rocket {
    return Rocket(
        rocketId = "",
        name = this?.name,
        type = this?.type
    )
}