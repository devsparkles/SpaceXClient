package com.devsparkle.spacexclient.domain.model.paged

class PageSummary<T> (
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
