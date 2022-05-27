package com.devsparkle.spacexclient.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devsparkle.spacexclient.main.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule
import android.content.Context
import com.devsparkle.spacexclient.data.launch.filter.LaunchRequestParametersCache
import com.devsparkle.spacexclient.data.launch.local.LaunchDao
import com.devsparkle.spacexclient.data.launch.remote.LaunchService
import com.devsparkle.spacexclient.data.launch.remote.repository.LaunchRepositoryImpl
import com.devsparkle.spacexclient.domain.use_case.GetCompanyDetail
import com.devsparkle.spacexclient.domain.use_case.GetFilteredLaunchList
import com.devsparkle.spacexclient.domain.use_case.GetRocketById
import com.devsparkle.spacexclient.main.MainViewModel
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.CoroutineDispatcher
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var api: LaunchService

    @Mock
    private lateinit var dao: LaunchDao

    @Mock
    private lateinit var context: Context

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        mockkStatic("com.devsparkle.spacexclient.utils.ContextExtKt")
        every {
            context.isNetworkAvailable()
        } returns true
//        testCoroutineRule.runBlockingTest {
//            `when`(api.fetchShowList()).thenReturn(emptyList())
//            `when`(dao.getShows()).thenReturn(emptyList())
//        }

        val repository = LaunchRepositoryImpl(api)

        testCoroutineRule.pauseDispatcher()

//        private val getRocketById: GetRocketById,
//        private val getFilteredLaunchList: GetFilteredLaunchList,
//        private val getCompanyDetail: GetCompanyDetail,
//        private val launchRequestParametersCache: LaunchRequestParametersCache,
//        private val coroutineContext: CoroutineDispatcher
//        val viewModel = MainViewModel(repository)

//        assertThat(viewModel.stateFlow.value, `is`(Resource.loading()))
//
//        testCoroutineRule.resumeDispatcher()
//
//        assertThat(viewModel.stateFlow.value, `is`(Resource.success(emptyList())))
    }

}