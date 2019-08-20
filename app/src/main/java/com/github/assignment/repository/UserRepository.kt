package com.github.assignment.repository

import com.github.assignment.network.requests.GithubService
import com.github.assignment.network.responses.User
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val githubService: GithubService
) : IUserRepository {

    companion object {
        private const val TAG = "UserRepository"
    }

    override fun loadUser(): Single<Response<List<User>>> {
        return githubService.getUsers()
    }
}