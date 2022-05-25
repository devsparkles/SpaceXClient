package com.devsparkle.spacexclient.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsparkle.spacexclient.base.livedata.SingleLiveEvent
import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.data.launch.filter.LaunchParameters
import com.devsparkle.spacexclient.data.launch.filter.LaunchRequestParametersCache
import com.devsparkle.spacexclient.domain.model.Company
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.use_case.GetCompanyDetail
import com.devsparkle.spacexclient.domain.use_case.GetFilteredLaunchList
import com.devsparkle.spacexclient.domain.use_case.GetLaunchList
import com.devsparkle.spacexclient.domain.use_case.ObserveLaunches
import com.devsparkle.spacexclient.domain.use_case.SaveLaunch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getFilteredLaunchList: GetFilteredLaunchList,
    private val getCompanyDetail: GetCompanyDetail,
    private val launchRequestParametersCache: LaunchRequestParametersCache,
    private val coroutineContext: CoroutineDispatcher
) : ViewModel() {

    private val _companyDetails = MutableLiveData<Resource<Company>>()
    val companyDetails: LiveData<Resource<Company>> = _companyDetails

    private val _launchListLiveData = SingleLiveEvent<Resource<List<Launch>?>>()
    val launchListLiveData: LiveData<Resource<List<Launch>?>> = _launchListLiveData


    fun getCompany() {
        viewModelScope.launch(coroutineContext) {
            _companyDetails.postValue(Resource.Loading())
            val result = withContext(Dispatchers.IO) {
                getCompanyDetail.invoke()
            }
            _companyDetails.postValue(result)
        }
    }

    fun getLaunches() {
        viewModelScope.launch(coroutineContext) {
            _launchListLiveData.postValue(Resource.Loading())
            val parameters = launchRequestParametersCache.getFromCache()
            val response = withContext(Dispatchers.IO) {
                getFilteredLaunchList.invoke(
                    page = parameters.page,
                    size = parameters.size,
                    orderBy = parameters.order,
                    launchSuccess = parameters.launchSuccess,
                    launchYear = parameters.launchYear
                )
            }
            if (response != null) {
                _launchListLiveData.postValue(Resource.of { response.docs })
            } else {
                _launchListLiveData.postValue(Resource.Error())
            }
        }
    }

    fun setLaunchParameters(parameters: LaunchParameters) {
        launchRequestParametersCache.addToCache(parameters)
    }

    fun getLaunchParameters(): LaunchParameters {
        return launchRequestParametersCache.getFromCache()
    }

}