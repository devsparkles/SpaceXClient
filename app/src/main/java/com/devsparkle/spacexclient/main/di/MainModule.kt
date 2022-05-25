package com.devsparkle.spacexclient.main.di

import com.devsparkle.spacexclient.data.launch.filter.LaunchRequestParametersCache
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.use_case.GetCompanyDetail
import com.devsparkle.spacexclient.domain.use_case.GetFilteredLaunchList
import com.devsparkle.spacexclient.domain.use_case.GetRocketById
import com.devsparkle.spacexclient.main.MainViewModel
import com.devsparkle.spacexclient.main.adapter.LaunchAdapter
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel {
        MainViewModel(
            get<GetRocketById>(),
            get<GetFilteredLaunchList>(),
            get<GetCompanyDetail>(),
            get<LaunchRequestParametersCache>(),
            get<CoroutineDispatcher>()
        )
    }


    factory { (clickCallback: ((Launch) -> Unit)) ->
        LaunchAdapter(
            clickCallback
        )
    }

}
