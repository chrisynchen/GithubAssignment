package com.github.assignment

import android.util.Log
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.github.assignment.network.requests.FetchUsersRequest
import com.github.assignment.network.responses.User
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
class MainPresenterTest {
    private lateinit var presenter: MainPresenter

    private val view: MainView = mock()
    private val fetchUsersRequest: FetchUsersRequest = mock()

    @Before
    fun setUp() {
        PowerMockito.mockStatic(Log::class.java)

        val immediate = object : Scheduler() {
            override fun createWorker(): Scheduler.Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

        presenter = MainPresenter(view, fetchUsersRequest)
    }

    @Test
    fun subscribe_failed() {
        // Given
        whenever(fetchUsersRequest.getUsers())
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
        val users = mutableListOf<User>().also {
            it.add(User("github",0,"","","",
                "","","","","","",
                "", "","","","",false))
        }
        val response = Response.success<List<User>>(users)
        whenever(fetchUsersRequest.getUsers())
            .thenReturn(Single.just(response))
        whenever(view.getUserTitle())
            .thenReturn("Users")

        // When
        presenter.subscribe()

        // Then
        verify(view).startLoading()
        verify(view).dismissLoading()
        verify(view).getUserTitle()
        verify(view).onFetchUsers(any())
    }
}