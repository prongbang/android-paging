package com.prongbang.paging.feature.user.data

import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.prongbang.paging.feature.user.model.Listing
import com.prongbang.paging.feature.user.model.User
import java.util.concurrent.Executors

interface UserRepository {

    suspend fun getUserByPaged(page: Int, limit: Int): Listing<User>

}

class DefaultUserRepository(
    private val dataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUserByPaged(page: Int, limit: Int): Listing<User> {

        val sourceFactory = UserDataSourceFactory(page, limit, dataSource)

        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = sourceFactory.toLiveData(
            pageSize = limit
        )

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoading
        }

        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
    }

}