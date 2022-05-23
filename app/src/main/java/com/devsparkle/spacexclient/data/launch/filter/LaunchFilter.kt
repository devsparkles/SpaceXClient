package com.devsparkle.spacexclient.data.launch.filter


class LaunchFilter {

    var defaultSorting: String = "year,desc"
    var sortBy: String = defaultSorting

    fun reset() {
        sortBy = "updated_at,desc"
    }

    fun isFilterApplied(): Boolean {
        return sortBy != defaultSorting
    }

}