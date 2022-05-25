package com.devsparkle.spacexclient.domain.repository.remote

import com.devsparkle.spacexclient.domain.model.Rocket

interface RocketRepository {
    suspend fun getRocketById(id: String): Rocket
}