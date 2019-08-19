package com.github.assignment.network.requests

import com.github.assignment.network.responses.User
import com.github.assignment.network.responses.UserDetails
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/users")
    fun getUsers(): Single<Response<List<User>>>

    @GET("/users/{login}")
    fun getUserDetails(@Path("login") login: String): Single<Response<UserDetails>>
}