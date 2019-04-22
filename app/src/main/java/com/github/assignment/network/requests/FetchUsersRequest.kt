package com.github.assignment.network.requests

import com.github.assignment.network.responses.User
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author chenchris on 2019/4/22.
 */
interface FetchUsersRequest {
    @GET("/users")
    fun getUsers(): Single<Response<List<User>>>
}