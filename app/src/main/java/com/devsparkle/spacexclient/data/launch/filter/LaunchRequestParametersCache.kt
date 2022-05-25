package com.devsparkle.spacexclient.data.launch.filter

import android.content.SharedPreferences
import com.google.gson.Gson

class LaunchRequestParametersCache(private val sharedPreferences: SharedPreferences) {

    fun addToCache(launchFilter: LaunchParameters) {
        val editor = sharedPreferences.edit()
        val launchFilterJSONString = Gson().toJson(launchFilter)
        editor.putString(LAUNCH_FILTER_TOKEN_KEY, launchFilterJSONString)
        editor.apply()
    }

    fun getFromCache(): LaunchParameters {
        val launchFilterJSONString = sharedPreferences.getString(LAUNCH_FILTER_TOKEN_KEY, null)
        if (!launchFilterJSONString.isNullOrEmpty()) {
            return Gson().fromJson(launchFilterJSONString, LaunchParameters::class.java)
        }
        return LaunchParameters()
    }

    private companion object {
        const val LAUNCH_FILTER_TOKEN_KEY = "LAUNCH_FILTER_TOKEN_KEY"
    }

}