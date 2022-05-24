package com.devsparkle.spacexclient.data.di


import com.devsparkle.spacexclient.data.company.CompanyRepositoryImpl
import com.devsparkle.spacexclient.data.company.remote.CompanyService
import com.devsparkle.spacexclient.data.launch.LaunchRepositoryImpl
import com.devsparkle.spacexclient.data.launch.remote.LaunchService
import com.devsparkle.spacexclient.data.rocket.remote.RocketService
import com.devsparkle.spacexclient.data.utils.SpaceXApi.Companion.createSpaceXRetrofit
import com.devsparkle.spacexclient.domain.repository.CompanyRepository
import com.devsparkle.spacexclient.domain.repository.LaunchRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


const val SPACEX_RETROFIT = "SPACEX_RETROFIT"

val remoteDataModule = module {

    factory {
        CompanyRepositoryImpl(
            get<CompanyService>()
        )
    }
    factory {
        LaunchRepositoryImpl(
            get<LaunchService>(),
            get<RocketService>()
        )
    }



    single(named(SPACEX_RETROFIT)) {
        createSpaceXRetrofit(androidContext())
    }

    factory {
        getCompanyService(
            get<Retrofit>(named(SPACEX_RETROFIT))
        )
    }

    factory {
        getLaunchService(
            get<Retrofit>(named(SPACEX_RETROFIT))
        )
    }

    factory {
        getRocketService(
            get<Retrofit>(named(SPACEX_RETROFIT))
        )
    }

}

private fun getCompanyService(retrofit: Retrofit): CompanyService =
    retrofit.create(CompanyService::class.java)

private fun getLaunchService(retrofit: Retrofit): LaunchService =
    retrofit.create(LaunchService::class.java)

private fun getRocketService(retrofit: Retrofit): RocketService =
    retrofit.create(RocketService::class.java)
