package com.devsparkle.spacexclient.base.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devsparkle.spacexclient.data.launch.entities.LaunchEntity
import com.devsparkle.spacexclient.data.launch.local.LaunchDao
import okhttp3.internal.concurrent.Task

/**
 * The Room Database that contains the Task table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [LaunchEntity::class], version = 1, exportSchema = false)
abstract class SpaceXDatabase : RoomDatabase() {

    abstract fun launchDao(): LaunchDao
}
