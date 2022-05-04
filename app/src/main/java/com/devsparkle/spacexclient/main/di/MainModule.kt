package com.devsparkle.spacexclient.main.di

import android.view.View
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.use_case.GetCompanyDetail
import com.devsparkle.spacexclient.domain.use_case.GetLaunchList
import com.devsparkle.spacexclient.main.MainViewModel
import com.devsparkle.spacexclient.main.adapter.LaunchAdapter
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel {
        MainViewModel(
            get<GetLaunchList>(),
            get<GetCompanyDetail>(),
            get<CoroutineDispatcher>()
        )
    }


    factory { (clickCallback: ((Launch) -> Unit)) ->
        LaunchAdapter(
            clickCallback
        )
    }

}
