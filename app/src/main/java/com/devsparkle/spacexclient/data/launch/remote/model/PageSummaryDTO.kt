package com.devsparkle.spacexclient.data.launch.remote.model

class PageSummaryDTO<T> (
    val docs: T?,
    val totalDocs: Int?,
    val offset: Int?,
    val limit: Int?,
    val totalPages: Int?,
    val page: Int?,
    val pagingCounter: Int?,
    val hasPrevPage: Boolean?,
    val prevPage: Int?
)
