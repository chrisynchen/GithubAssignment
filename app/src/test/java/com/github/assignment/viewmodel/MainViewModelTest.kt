package com.github.assignment.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.assignment.RxImmediateSchedulerRule
import com.github.assignment.network.requests.GithubService
import com.github.assignment.network.responses.User
import com.github.assignment.repository.IUserRepository
import com.github.assignment.repository.UserRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.Response
import kotlin.test.assertEquals


/**
 * @author chenchris on 2019/4/24.
 */
@RunWith(PowerMockRunner::class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(Log::class)
class MainViewModelTest {

    @Rule
    var instantRule: TestRule = InstantTaskExecutorRule()

    @Rule
    var rxRule: TestRule = RxImmediateSchedulerRule()

    private lateinit var viewModel: MainViewModel

    private val githubService: GithubService = mock()
    private val userRepository: IUserRepository = UserRepository(githubService)

    @Before
    fun setUp() {
        PowerMockito.mockStatic(Log::class.java)

        viewModel = MainViewModel(userRepository)
    }

    @Test
    fun subscribe_failed() {
        // Given
        whenever(githubService.getUsers())
            .thenReturn(Single.error(Throwable()))

        // When
        viewModel.onRefresh()

        // Then
        assertEquals(false, viewModel.progressVisibility.value)
    }

    @Test
    fun subscribe_success() {
        // Given
        val users = mutableListOf<User>().also {
            it.add(
                User(
                    "github", 0, "", "", "",
                    "", "", "", "", "", "",
                    "", "", "", "", "", false
                )
            )

            it.add(
                User(
                    "facebook", 0, "", "", "",
                    "", "", "", "", "", "",
                    "", "", "", "", "", false
                )
            )
        }
        val response = Response.success<List<User>>(users)
        whenever(githubService.getUsers())
            .thenReturn(Single.just(response))

        // When
        viewModel.onRefresh()

        // Then
        assertEquals(false, viewModel.progressVisibility.value)
        assertEquals(3, viewModel.items.value?.size)
    }
}