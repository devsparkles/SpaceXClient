package com.devsparkle.spacexclient.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.domain.model.Company
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.domain.use_case.GetCompanyDetail
import com.devsparkle.spacexclient.domain.use_case.GetLaunchList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MainViewModel(
    private val getLaunchList: GetLaunchList,
    private val getCompanyDetail: GetCompanyDetail,
    private val coroutineContext: CoroutineDispatcher
) : ViewModel() {

    private val _launches = MutableLiveData<List<Launch>>()
    val launches: LiveData<List<Launch>> = _launches

    private val _companyDetails = MutableLiveData<Resource<Company>>()
    val companyDetails: LiveData<Resource<Company>> = _companyDetails

    fun getCompanyDetail() {
        viewModelScope.launch(coroutineContext) {
            _companyDetails.postValue(Resource.Loading())
            val response = getCompanyDetail.invoke()
            _companyDetails.postValue(response)
        }
    }

    fun getAllLaunches() {
        viewModelScope.launch(coroutineContext) {
            val response = getLaunchList.invoke()
            _launches.postValue(response)
        }
    }


}