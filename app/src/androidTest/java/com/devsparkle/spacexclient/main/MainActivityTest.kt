package com.devsparkle.spacexclient.main

//import androidx.test.core.app.ActivityScenario
//import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnit4
import com.devsparkle.spacexclient.domain.repository.remote.LaunchRepository
//import com.github.javafaker.Faker
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.whenever


@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    private val mockLaunchRepository: LaunchRepository by inject()
  //  private var faker = Faker()

    @Test
    fun onLaunchButtonIsDisplayed() = runTest {
        declareMock<LaunchRepository> {
            runTest {
                whenever(getAllLaunches())
                    .thenReturn(
                        null
                    )
            }
        }

 //       ActivityScenario.launch(MainActivity::class.java)
//        Espresso.onView(withId(R.id.buttonNewJoke))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


}