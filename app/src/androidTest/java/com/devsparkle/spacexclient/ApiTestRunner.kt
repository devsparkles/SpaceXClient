package com.devsparkle.spacexclient;

import android.app.Application
import androidx.test.runner.AndroidJUnitRunner
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTestRunner : AndroidJUnitRunner() {

    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)
//        loadKoinModules(module {
//            single(override = true) { createTestWebService<UserDatasource>(get()) }
//        })
    }

    private inline fun <reified T> createTestWebService(okHttpClient: OkHttpClient): T {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://localhost:$MOCK_WEB_SERVER_PORT/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(T::class.java)
    }

    companion object {
        const val MOCK_WEB_SERVER_PORT = 4007
    }
}