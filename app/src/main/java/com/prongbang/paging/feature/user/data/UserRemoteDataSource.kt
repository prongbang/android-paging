package com.prongbang.paging.feature.user.data

import com.prongbang.paging.feature.user.api.UserService
import com.prongbang.paging.utils.safeApiCall

class UserRemoteDataSource(
    private val service: UserService
) {

    suspend fun getUserByPaged(page: Int, limit: Int) = safeApiCall(service.getUserByPaged(page, limit))

}