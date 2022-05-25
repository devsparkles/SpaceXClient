package com.devsparkle.spacexclient.data.launch.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Immutable model class for a Launch. In order to compile with Room, we can't use @JvmOverloads to
 * generate multiple constructors.
 *
 * @param name       name of the launch
 * @param imageUrlSmall  link for the image large
 * @param imageUrlLarge  link for the image large
 * @param dateUtc  date in the utc format
 * @param dateLocal          date in the local format
 * @param rocketId          id of the rocket that did the launch
 * @param success          success of the launch
 */
@Entity(tableName = "launches")
data class LaunchEntity @JvmOverloads constructor(
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "imageUrlSmall") var imageUrlSmall: String = "",
    @ColumnInfo(name = "imageUrlLarge") var imageUrlLarge: String = "",
    @ColumnInfo(name = "dateUtc") var dateUtc: String = "",
    @ColumnInfo(name = "dateLocal") var dateLocal: String = "",
    @ColumnInfo(name = "rocketId") var rocketId: String = "",
    @ColumnInfo(name = "success") var success: Boolean = false,
    @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()
) {

}
