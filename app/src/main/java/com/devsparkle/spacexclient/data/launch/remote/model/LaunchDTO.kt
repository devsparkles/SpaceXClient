package com.devsparkle.spacexclient.data.launch.remote.model

import com.google.gson.annotations.SerializedName

data class LaunchDTO(
    val name: String?,
    val links: LinkDTO?,
    @SerializedName("date_utc")
    val dateAndTime: String?,
    @SerializedName("rocket")
    val rocketId: String?,
    val success: Boolean?
)

