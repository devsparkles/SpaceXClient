package com.devsparkle.spacexclient.domain.use_case

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.domain.model.Company
import com.devsparkle.spacexclient.domain.repository.remote.CompanyRepository

class GetCompanyDetail(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(): Resource<Company> {
        return companyRepository.getCompanyDetail()
    }
}