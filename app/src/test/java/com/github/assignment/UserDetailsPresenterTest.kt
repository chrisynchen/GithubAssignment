package com.github.assignment

import android.util.Log
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.github.assignment.network.requests.FetchUserDetailsRequest
import com.github.assignment.network.responses.UserDetails
import com.github.assignment.presenter.UserDetailsPresenter
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.Response
import java.util.concurrent.Executor

/**
 * @author chenchris on 2019/4/24.
 */
@RunWith(PowerMockRunner::class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(Log::class)
class UserDetailsPresenterTest {
    private lateinit var presenter: UserDetailsPresenter

    private val view: UserDetailsView = mock()
    private val fetchUserDetailsRequest: FetchUserDetailsRequest = mock()
    private val login = "github"

    @Before
    fun setUp() {
        PowerMockito.mockStatic(Log::class.java)

        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

        presenter = UserDetailsPresenter(view, fetchUserDetailsRequest, login)
    }

    @Test
    fun subscribe_failed() {
        // Given
        whenever(fetchUserDetailsRequest.getUserDetails(login))
            .thenReturn(Single.error(Throwable()))

        // When
        presenter.subscribe()

        // Then
        verify(view).startLoading()
        verify(view).dismissLoading()
    }

    @Test
    fun subscribe_success() {
        // Given
        val userDetails = UserDetails(
            "", 0, "", "", "", "", "",
            "", "", "", "", "", "", "",
            "", "", false, "", "", "", "", "",
            "", "", 0, 0, 0, 0, "",
            ""
        )
        val response = Response.success(userDetails)
        whenever(fetchUserDetailsRequest.getUserDetails(login))
            .thenReturn(Single.just(response))

        // When
        presenter.subscribe()

        // Then
        verify(view).startLoading()
        verify(view).dismissLoading()
        verify(view).onFetchUserDetails(any())
    }
}