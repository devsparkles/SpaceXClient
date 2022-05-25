package com.devsparkle.spacexclient.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.devsparkle.spacexclient.base.local.SpaceXDatabase
import com.devsparkle.spacexclient.data.launch.filter.LaunchRequestParametersCache
import com.devsparkle.spacexclient.data.launch.local.LaunchDao
import com.devsparkle.spacexclient.data.launch.local.repository.LaunchLocalRepositoryImpl
import com.devsparkle.spacexclient.domain.repository.local.LaunchLocalRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val localDataModule = module {

    factory {
        LaunchLocalRepositoryImpl(
            get<LaunchDao>()
        ) as LaunchLocalRepository
    }

    single {
        get<SpaceXDatabase>().launchDao()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            SpaceXDatabase::class.java,
            SPACE_X_DATABASE
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    single {
        LaunchRequestParametersCache(
            get<SharedPreferences>()
        )
    }

    single {
        getSharedPreferences(androidContext())
    }
}
const val SPACE_X_DATABASE = "SPACE_X_DATABASE"


private fun getSharedPreferences(context: Context) =
    PreferenceManager.getDefaultSharedPreferences(context)