package com.devsparkle.spacexclient.app

import android.app.Application
import com.devsparkle.spacexclient.app.di.appModule
import com.devsparkle.spacexclient.app.di.networkModule
import com.devsparkle.spacexclient.base.di.baseModule
import com.devsparkle.spacexclient.data.di.dataModule
import com.devsparkle.spacexclient.domain.di.domainModule
import com.devsparkle.spacexclient.main.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpaceXClientApp : Application() {


    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }


    private fun setupKoin() {
        startKoin {
            androidContext(this@SpaceXClientApp)
            modules(
                listOf(
                    appModule,
                    baseModule,
                    networkModule,
                    domainModule,
                    dataModule,
                    mainModule
                )
            )
        }
    }
}