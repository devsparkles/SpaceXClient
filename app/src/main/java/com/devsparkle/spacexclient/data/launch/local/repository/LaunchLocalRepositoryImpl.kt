package com.devsparkle.spacexclient.data.launch.local.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.devsparkle.spacexclient.data.launch.local.LaunchDao
import com.devsparkle.spacexclient.data.launch.mapper.toEntity
import com.devsparkle.spacexclient.data.launch.mapper.toView
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.repository.local.LaunchLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LaunchLocalRepositoryImpl(
    private var launchDao: LaunchDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LaunchLocalRepository {

    override fun observeAllLaunches(): LiveData<List<Launch>> {
        return launchDao.observeLaunches().map {
            it.map { launchEntity ->
                launchEntity.toView()
            }
        }
    }

    override suspend fun saveLaunch(launch: Launch) = withContext(ioDispatcher) {
        launchDao.insertLaunch(
            launch.toEntity()
        )
    }

    override suspend fun deleteAllLaunches() = withContext(ioDispatcher) {
        launchDao.deleteLaunches()
    }


}