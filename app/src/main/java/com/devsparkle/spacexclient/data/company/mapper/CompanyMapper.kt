package com.devsparkle.spacexclient.data.company.mapper

import com.devsparkle.spacexclient.base.resource.Resource
import com.devsparkle.spacexclient.data.company.model.CompanyDTO
import com.devsparkle.spacexclient.domain.model.Company

class CompanyMapper


fun Resource<CompanyDTO?>.toDomain(): Resource<Company> {
    return when (this) {
        is Resource.Success -> Resource.Success(this.value().toDomain())
        is Resource.SuccessWithoutContent -> Resource.SuccessWithoutContent()
        is Resource.Error -> Resource.Error(this.error())
        is Resource.Loading -> Resource.Loading()
    }
}

fun List<CompanyDTO>.toDomain(): MutableList<Company> {
    val result: MutableList<Company> = mutableListOf()
    this.forEach { result.add(it.toDomain()) }
    return result
}


fun CompanyDTO?.toDomain(): Company {
    return Company(
        this?.name,
        this?.founder,
        this?.founded.toString(),
        this?.employees.toString(),
        this?.launch_sites.toString(),
        this?.valuation.toString()
    )
}


