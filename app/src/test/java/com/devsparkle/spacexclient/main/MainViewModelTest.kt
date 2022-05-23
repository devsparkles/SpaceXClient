package com.devsparkle.spacexclient.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devsparkle.spacexclient.data.company.remote.CompanyService
import com.devsparkle.spacexclient.data.launch.LaunchRepositoryImpl
import com.devsparkle.spacexclient.data.launch.remote.LaunchService
import com.devsparkle.spacexclient.domain.repository.LaunchRepository
import com.devsparkle.spacexclient.domain.use_case.GetCompanyDetail
import com.devsparkle.spacexclient.domain.use_case.GetLaunchList
import com.devsparkle.spacexclient.utils.CommonTestDataUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(MockitoJUnitRunner.Silent::class)
class MainViewModelTest {

//    @get:Rule
//    val coroutineRule = MainCoroutineRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    fun <T> any(): T = Mockito.any<T>()


    private val mainThreadSurrogate =
        newSingleThreadContext("Mocked UI thread")

    val server = MockWebServer()

    @Mock
    private lateinit var launchService: LaunchService
    @Mock
    private lateinit var companyService: CompanyService

    @Mock
    private lateinit var getCompanyDetail: GetCompanyDetail

    @Mock
    private lateinit var getLaunchList: GetLaunchList

    val dispatcher: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(
            request: RecordedRequest
        ): MockResponse {
            return CommonTestDataUtil.nonInterceptedDispatch(request)
                ?: MockResponse().setResponseCode(404)
        }
    }

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        server.setDispatcher(dispatcher)
        server.start()
        val logger = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .build()
        companyService = Retrofit.Builder()
            .baseUrl(server.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(CompanyService::class.java)
        launchService = Retrofit.Builder()
            .baseUrl(server.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(LaunchService::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

//    @Test
//    fun `Filter launche by year`() =
//        coroutineRule.testDispatcher.runBlockingTest{
//
//            //Given
//            val expected = "name of launch"
//
//            val repo = LaunchRepositoryImpl()
//        }

//
//    @Test
//    fun testSomeUI() = runBlocking {
//        val searchForCompanionViewModel =
//            MainViewModel(
//                getLaunchList,
//                getCompanyDetail
//            )
//
//        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
//
//        }
//    }

    // 4
    @Test
    fun call_to_fetch_company_result() {
//        val searchForCompanionViewModel =
//            MainViewModel(
//                getLaunchList,
//                getCompanyDetail
//            )
////        searchForCompanionViewModel.companionLocation.value = "30318"
//// 1
//        val countDownLatch = CountDownLatch(1)
//        searchForCompanionViewModel.searchForCompanions()
//// 2
//        searchForCompanionViewModel.animals.observeForever {
//            countDownLatch.countDown()
//        }
//// 3
//        countDownLatch.await(2, TimeUnit.SECONDS)
//        Assert.assertEquals(
//            2,
//            searchForCompanionViewModel.animals.value!!.size
//        )
    }
//
//    fun callSearchForCompanionWithALocationAndWaitForVisibilityResult(location: String): SearchForCompanionViewModel {
//        val searchForCompanionViewModel =
//            SearchForCompanionViewModel(petFinderService)
//        searchForCompanionViewModel.companionLocation.value = location
//        val countDownLatch = CountDownLatch(1)
//        searchForCompanionViewModel.searchForCompanions()
//        searchForCompanionViewModel.noResultsViewVisiblity
//            .observeForever {
//                countDownLatch.countDown()
//            }
//
//        countDownLatch.await(2, TimeUnit.SECONDS)
//        return searchForCompanionViewModel
//    }
//
//    @Test
//    fun call_to_searchForCompanions_with_results_sets_the_visibility_of_no_results_to_INVISIBLE() {
//        val searchForCompanionViewModel =
//            callSearchForCompanionWithALocationAndWaitForVisibilityResult("30318")
//        Assert.assertEquals(
//            INVISIBLE,
//            searchForCompanionViewModel.noResultsViewVisiblity.value
//        )
//    }
//
//    @Test
//    fun call_to_searchForCompanions_with_no_results_sets_the_visibility_of_no_results_to_VISIBLE() {
//        val searchForCompanionViewModel =
//            callSearchForCompanionWithALocationAndWaitForVisibilityResult("90210")
//        Assert.assertEquals(
//            VISIBLE,
//            searchForCompanionViewModel.noResultsViewVisiblity.value
//        )
//    }
}