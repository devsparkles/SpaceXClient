package com.devsparkle.spacexclient.data.launch.remote.model

import com.google.gson.annotations.SerializedName

data class LaunchDTO(
    val name: String?,
    @SerializedName("date_utc")
    val dateAndTime: String?,
    val rocket: String?,
    val success: Boolean?
)

