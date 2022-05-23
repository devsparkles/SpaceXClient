package com.devsparkle.spacexclient.data.launch.filter

import android.content.SharedPreferences
import com.google.gson.Gson

class LaunchFilterCache(private val sharedPreferences: SharedPreferences) {

    fun addToCache(maintenanceFilter: LaunchFilter) {
        val editor = sharedPreferences.edit()
        val maintenanceFilterJSONString = Gson().toJson(maintenanceFilter)
        editor.putString(LAUNCH_FILTER_TOKEN_KEY, maintenanceFilterJSONString)
        editor.apply()
    }

    fun getFromCache(): LaunchFilter {
        val maintenanceFilterJSONString = sharedPreferences.getString(LAUNCH_FILTER_TOKEN_KEY, null)
        if (!maintenanceFilterJSONString.isNullOrEmpty()) {
            return Gson().fromJson(maintenanceFilterJSONString, LaunchFilter::class.java)
        }
        return LaunchFilter()
    }

    private companion object {
        const val LAUNCH_FILTER_TOKEN_KEY = "LAUNCH_FILTER_TOKEN_KEY"
    }

}