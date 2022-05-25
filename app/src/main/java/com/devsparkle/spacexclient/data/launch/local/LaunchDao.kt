package com.devsparkle.spacexclient.data.launch.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devsparkle.spacexclient.data.launch.entities.LaunchEntity

/**
 * Data Access Object for the launch table.
 */
@Dao
interface LaunchDao {

    /**
     * Observes list of launches.
     *
     * @return all launches.
     */
    @Query("SELECT * FROM launches")
    fun observeLaunches(): LiveData<List<LaunchEntity>>


    /**
     * Insert a launch in the database. If the launch already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunch(task: LaunchEntity)


    /**
     * Delete all launches.
     */
    @Query("DELETE FROM launches")
    suspend fun deleteLaunches()


}