package com.devsparkle.spacexclient.domain.use_case

import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.model.paged.PageSummary
import com.devsparkle.spacexclient.domain.repository.remote.LaunchRepository

class GetFilteredLaunchList(
    private val launchRepository: LaunchRepository
) {
    suspend operator fun invoke(
        page: Int, size: Int,
        launchYear: String?,
        launchSuccess: Boolean?,
        orderBy: String?
    ): PageSummary<List<Launch>>? {
        return launchRepository.filter(page, size, launchYear, launchSuccess, orderBy)
    }
}
