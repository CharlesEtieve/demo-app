package com.demo.app.data.vendors.network.api

import com.demo.app.data.models.response.UserListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    // I added api/1.0 here but it should be in base_url
    // I did that because no path for request induces strange behavior
    @GET("api/1.0")
    fun getUserPage(
        @Query("seed") seed: String,
        @Query("results") results: String,
        @Query("page") page: String,
    ): Single<UserListResponse>
}