package com.devsparkle.spacexclient.base.di

import com.devsparkle.spacexclient.BuildConfig
import com.devsparkle.spacexclient.main.MainActivity
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val SPACE_X_URL = "SPACE_X_URL"

val urlsModule = module {
    single(named(SPACE_X_URL)){ BuildConfig.API_URL }
}

val baseModule = module {

    factory {
        getCoroutinesDispatchersIo()
    }
}

private fun getCoroutinesDispatchersIo() = Dispatchers.IO
