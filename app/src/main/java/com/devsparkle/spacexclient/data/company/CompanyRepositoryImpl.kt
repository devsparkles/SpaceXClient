package com.devsparkle.spacexclient.data.company

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.data.company.mapper.toDomain
import com.devsparkle.spacexclient.data.company.model.CompanyDTO
import com.devsparkle.spacexclient.data.company.remote.CompanyService
import com.devsparkle.spacexclient.domain.model.Company
import com.devsparkle.spacexclient.domain.repository.CompanyRepository

class CompanyRepositoryImpl(private val companyService: CompanyService):  CompanyRepository {

    override suspend fun getCompanyDetail(): Resource<Company> {
        val response = Resource.of<CompanyDTO?> {
            companyService.getCompanyInfo()
        }

      return response.toDomain()
    }
}