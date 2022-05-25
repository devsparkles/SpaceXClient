package com.devsparkle.spacexclient.data.launch.mapper

import com.devsparkle.spacexclient.data.launch.remote.model.LaunchDTO
import com.devsparkle.spacexclient.data.launch.remote.model.PageSummaryDTO
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.model.paged.PageSummary



fun PageSummaryDTO<List<LaunchDTO>?>.toDomain(): PageSummary<List<Launch>> {
    return PageSummary<List<Launch>>(
        totalPages = this.totalPages,
        totalDocs = this.totalDocs,
        offset = this.offset,
        limit = this.limit,
        page = this.page,
        pagingCounter = this.pagingCounter,
        hasPrevPage = this.hasPrevPage,
        prevPage = this.prevPage,
        docs = (this.docs as List<LaunchDTO>).toDomain(),
    )
}

