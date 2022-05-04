package com.devsparkle.spacexclient.domain.di

import com.devsparkle.spacexclient.domain.repository.CompanyRepository
import com.devsparkle.spacexclient.domain.repository.LaunchRepository
import com.devsparkle.spacexclient.domain.use_case.GetCompanyDetail
import com.devsparkle.spacexclient.domain.use_case.GetLaunchList
import org.koin.dsl.module

val domainModule = module {

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
