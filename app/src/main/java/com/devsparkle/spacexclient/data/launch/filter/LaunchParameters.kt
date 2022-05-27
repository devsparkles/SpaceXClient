package com.devsparkle.spacexclient.data.launch.filter


data class LaunchParameters(
    var order: String? = Order.ASC.name,
    var launchYear: String? = "2022",
    var launchSuccess: Boolean? = true,
    var page: Int = 0,
    var size: Int = 5
)

