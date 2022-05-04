package com.devsparkle.spacexclient.domain.model

data class Launch(
    val missionPatchSmallImageUrl: String,
    val missionPatchLargeImageUrl: String,
    val missionName: String,
    val date: String,
    val time: String,
    val rocket: Rocket,
    val since: Boolean,
    val days: String,
    val success: Boolean
)