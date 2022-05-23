package com.devsparkle.spacexclient.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.data.launch.filter.LaunchFilterCache
import com.devsparkle.spacexclient.domain.model.Company
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.use_case.GetCompanyDetail
import com.devsparkle.spacexclient.domain.use_case.GetLaunchList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getLaunchList: GetLaunchList,
    private val getCompanyDetail: GetCompanyDetail,
    private val launchFilterCache: LaunchFilterCache,
    private val coroutineContext: CoroutineDispatcher
) : ViewModel() {

    private val _launches = MutableLiveData<List<Launch>>()
    val launches: LiveData<List<Launch>> = _launches

    private val _companyDetails = MutableLiveData<Resource<Company>>()
    val companyDetails: LiveData<Resource<Company>> = _companyDetails

    fun isFilterApplied(): Boolean {
        val filter = launchFilterCache.getFromCache()
        return filter.isFilterApplied()
    }

    fun getCompanyDetail() {
        viewModelScope.launch(coroutineContext) {
            _companyDetails.postValue(Resource.Loading())

            val result = withContext(Dispatchers.IO) {
                getCompanyDetail.invoke()
            }
            _companyDetails.postValue(result)
        }
    }

    fun getAllLaunches() {
        viewModelScope.launch(coroutineContext) {
            val result = withContext(Dispatchers.IO) {
                getLaunchList.invoke()
            }
            _launches.postValue(result)
        }
    }


}