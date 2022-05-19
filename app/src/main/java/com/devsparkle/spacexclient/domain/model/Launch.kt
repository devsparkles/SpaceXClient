package com.devsparkle.spacexclient.domain.model

data class Launch(
    val missionPatchSmallImageUrl: String?,
    val missionPatchLargeImageUrl: String?,
    val missionName: String?,
    val dateUtc: String?,
    val dateLocal: String?,
    val rocket: Rocket?,
    val success: Boolean?
)