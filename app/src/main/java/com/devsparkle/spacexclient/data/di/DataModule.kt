package com.devsparkle.spacexclient.data.di

import com.devsparkle.spacexclient.data.company.CompanyRepositoryImpl
import com.devsparkle.spacexclient.data.company.remote.CompanyService
import com.devsparkle.spacexclient.data.launch.LaunchRepositoryImpl
import com.devsparkle.spacexclient.data.launch.remote.LaunchService
import com.devsparkle.spacexclient.data.utils.SpaceXApi.Companion.createSpaceXRetrofit
import com.devsparkle.spacexclient.domain.repository.CompanyRepository
import com.devsparkle.spacexclient.domain.repository.LaunchRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


const val SPACEX_RETROFIT = "SPACEX_RETROFIT"

val dataModule = module {

    factory {
        CompanyRepositoryImpl(
            get<CompanyService>()
        ) as CompanyRepository
    }
    factory {
        LaunchRepositoryImpl(
            get<LaunchService>()
        ) as LaunchRepository
    }



    single(named(SPACEX_RETROFIT)) {
        createSpaceXRetrofit()
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
}

private fun getCompanyService(retrofit: Retrofit): CompanyService =
    retrofit.create(CompanyService::class.java)

private fun getLaunchService(retrofit: Retrofit): LaunchService =
    retrofit.create(LaunchService::class.java)

