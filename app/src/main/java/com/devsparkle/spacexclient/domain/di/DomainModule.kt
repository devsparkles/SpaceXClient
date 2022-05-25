package com.devsparkle.spacexclient.domain.di

import com.devsparkle.spacexclient.domain.repository.local.LaunchLocalRepository
import com.devsparkle.spacexclient.domain.repository.remote.CompanyRepository
import com.devsparkle.spacexclient.domain.repository.remote.LaunchRepository
import com.devsparkle.spacexclient.domain.use_case.GetCompanyDetail
import com.devsparkle.spacexclient.domain.use_case.GetFilteredLaunchList
import com.devsparkle.spacexclient.domain.use_case.GetLaunchList
import com.devsparkle.spacexclient.domain.use_case.ObserveLaunches
import com.devsparkle.spacexclient.domain.use_case.SaveLaunch
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetFilteredLaunchList(
            get<LaunchRepository>()
        )
    }

    factory {
        SaveLaunch(
            get<LaunchLocalRepository>()
        )
    }

    factory {
        ObserveLaunches(
            get<LaunchLocalRepository>()
        )
    }

    factory {
        GetLaunchList(
            get<LaunchRepository>()
        )
    }

    factory {
        GetCompanyDetail(
            get<CompanyRepository>()
        )
    }

}
