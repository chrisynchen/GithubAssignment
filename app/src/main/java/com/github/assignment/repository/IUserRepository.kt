package com.github.assignment.repository

import com.github.assignment.network.responses.User
import io.reactivex.Single
import retrofit2.Response

interface IUserRepository {
    fun loadUser(): Single<Response<List<User>>>
}