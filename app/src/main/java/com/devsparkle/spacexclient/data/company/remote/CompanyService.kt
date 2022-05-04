package com.devsparkle.spacexclient.data.company.remote

import com.devsparkle.spacexclient.data.company.model.CompanyDTO
import retrofit2.http.GET

interface CompanyService {

    @GET("/company")
    suspend fun getCompanyInfo(): CompanyDTO?
}