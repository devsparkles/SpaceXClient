package com.devsparkle.spacexclient.data.launch.remote.model

import com.google.gson.annotations.SerializedName

data class LaunchDTO(
    @SerializedName("flight_number")
    val flightNumber: String?,
    val name: String?,
    val links: LinkDTO?,
    @SerializedName("date_utc")
    val dateUtc: String?,
    @SerializedName("date_local")
    val dateLocal: String?,
    @SerializedName("rocket")
    val rocketId: String?,
    val success: Boolean?
)

