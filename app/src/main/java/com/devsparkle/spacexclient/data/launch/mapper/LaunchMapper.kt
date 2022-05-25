package com.devsparkle.spacexclient.data.launch.mapper

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.data.launch.entities.LaunchEntity
import com.devsparkle.spacexclient.data.launch.remote.model.LaunchDTO
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.model.Rocket


fun Resource<LaunchDTO?>.toDomain(): Resource<Launch> {
    return when (this) {
        is Resource.Success -> Resource.Success(this.value().toDomain())
        is Resource.SuccessWithoutContent -> Resource.SuccessWithoutContent()
        is Resource.Error -> Resource.Error(this.error())
        is Resource.Loading -> Resource.Loading()
    }
}

fun List<LaunchDTO>.toDomain(): MutableList<Launch> {
    val result: MutableList<Launch> = mutableListOf()
    this.forEach { result.add(it.toDomain()) }
    return result
}



fun LaunchDTO?.toDomain(): Launch {
    return Launch(
        missionName = this?.name,
        success = this?.success,
        dateUtc = this?.dateUtc,
        dateLocal = this?.dateLocal,
        missionPatchLargeImageUrl = this?.links?.patch?.large,
        missionPatchSmallImageUrl = this?.links?.patch?.small,
        rocket = Rocket(
            rocketId = this?.rocketId,
            "", ""
        )
    )
}

fun Launch?.toEntity(): LaunchEntity {
    return LaunchEntity(
        name = this?.missionName ?: "",
        success = this?.success ?: false,
        dateUtc = this?.dateUtc ?: "",
        dateLocal = this?.dateLocal ?: "",
        imageUrlSmall = this?.missionPatchSmallImageUrl ?: "",
        imageUrlLarge = this?.missionPatchLargeImageUrl ?: "",
        rocketId = this?.rocket?.rocketId ?: ""
    )
}

fun LaunchEntity?.toView(): Launch {
    return Launch(
        missionName = this?.name,
        success = this?.success,
        dateUtc = this?.dateUtc,
        dateLocal = this?.dateLocal,
        missionPatchLargeImageUrl = this?.imageUrlLarge,
        missionPatchSmallImageUrl = this?.imageUrlSmall,
        rocket = Rocket(
            rocketId = this?.rocketId,
            "", ""
        )
    )
}

