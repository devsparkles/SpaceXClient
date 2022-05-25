package com.devsparkle.spacexclient.domain.repository.remote

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.domain.model.Company

interface CompanyRepository {
    suspend fun getCompanyDetail(): Resource<Company>
}