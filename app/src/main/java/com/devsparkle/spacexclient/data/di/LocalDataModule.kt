package com.devsparkle.spacexclient.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.devsparkle.spacexclient.data.launch.filter.LaunchFilterCache
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val localDataModule = module {

    single {
        LaunchFilterCache(
            get<SharedPreferences>()
        )
    }

    single {
        getSharedPreferences(androidContext())
    }
}

private fun getSharedPreferences(context: Context) =
    PreferenceManager.getDefaultSharedPreferences(context)