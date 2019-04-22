package com.github.assignment.network.requests

import com.github.assignment.network.responses.UserDetails
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author chenchris on 2019/4/24.
 */
interface FetchUserDetailsRequest {
    @GET("/users/{login}")
    fun getUserDetails(@Path("login") login: String): Single<Response<UserDetails>>
}