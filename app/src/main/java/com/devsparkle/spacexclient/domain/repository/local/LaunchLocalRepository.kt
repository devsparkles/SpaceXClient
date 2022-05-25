package com.devsparkle.spacexclient.domain.repository.local

import androidx.lifecycle.LiveData
import com.devsparkle.spacexclient.domain.model.Launch

interface LaunchLocalRepository {

    fun observeAllLaunches(): LiveData<List<Launch>>

    suspend fun saveLaunch(launch: Launch)

    suspend fun deleteAllLaunches()
}