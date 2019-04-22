package com.github.assignment.network

import com.github.assignment.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author chenchris on 2019/4/22.
 */
class ApiManager private constructor(okHttpClient: OkHttpClient = OkHttpClient(),
                                     private val retrofit: Retrofit = Retrofit.Builder()
                                         .baseUrl(BuildConfig.API_BASE_URL)
                                         .addConverterFactory(GsonConverterFactory.create())
                                         .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                         .client(okHttpClient)
                                         .build()) {

    companion object {

        private val apiManagerInstance: ApiManager by lazy {
            ApiManager()
        }

        @JvmStatic
        fun getInstance(): ApiManager {
            return apiManagerInstance
        }
    }

    fun <T> create(service: Class<T>): T {
        return apiManagerInstance.retrofit.create(service)
    }
}