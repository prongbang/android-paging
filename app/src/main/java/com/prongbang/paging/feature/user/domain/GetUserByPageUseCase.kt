package com.prongbang.paging.feature.user.domain

import com.prongbang.paging.feature.user.data.UserRepository
import com.prongbang.paging.feature.user.model.Listing
import com.prongbang.paging.feature.user.model.User

class GetUserByPageUseCase(
    private val repository: UserRepository
) {
    suspend fun getUserByPaged(page: Int, limit: Int): Listing<User> {

        return repository.getUserByPaged(page, limit)
    }

}