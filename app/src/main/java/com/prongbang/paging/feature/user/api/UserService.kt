package com.prongbang.paging.feature.user.api

import com.prongbang.paging.feature.user.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("users")
    fun getUserByPaged(@Query("_page") page: Int, @Query("_limit") limit: Int): Deferred<Response<List<User>>>

}