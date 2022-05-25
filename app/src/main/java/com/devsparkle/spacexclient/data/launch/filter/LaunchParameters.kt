package com.devsparkle.spacexclient.data.launch.filter


data class LaunchParameters(
    var order: String = Order.ASC.name,
    var launchYear: Int = 2022,
    var launchSuccess: Boolean = true,
    var page: Int = 0,
    var size: Int = 10
) {
    fun isFilterApplied(): Boolean {
        return order != Order.ASC.name || launchYear != 2022
    }
}

