package com.devsparkle.spacexclient.app

import android.app.Application
import android.util.Log
import com.devsparkle.spacexclient.base.di.baseModule
import com.devsparkle.spacexclient.base.di.urlsModule
import com.devsparkle.spacexclient.data.di.localDataModule
import com.devsparkle.spacexclient.data.di.remoteDataModule
import com.devsparkle.spacexclient.domain.di.domainModule
import com.devsparkle.spacexclient.main.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException

class SpaceXClientApp : Application() {


    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }


    private fun setupKoin() {
        try {
            startKoin {
                androidContext(this@SpaceXClientApp)
                modules(
                    listOf(
                        urlsModule,
                        // shared module
                        baseModule,
                        // data remote and local module
                        localDataModule,
                        remoteDataModule,
                        // dto objects and use cases
                        domainModule,
                        // domain modules
                        mainModule
                    )
                )
            }

        } catch (koinAlreadyStartedException: KoinAppAlreadyStartedException) {
            Log.i(
                "CodingCompanionFinder",
                "KoinAppAlreadyStartedException, should only happen in tests"
            )
        }
    }
}