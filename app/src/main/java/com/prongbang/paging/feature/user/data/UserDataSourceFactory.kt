package com.prongbang.paging.feature.user.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.prongbang.paging.feature.user.model.User

class UserDataSourceFactory(
    private val page: Int,
    private val limit: Int,
    private val dataSource: UserRemoteDataSource
)  : DataSource.Factory<Int, User>() {

    val sourceLiveData = MutableLiveData<UserPageKeyedDataSource>()

    override fun create(): DataSource<Int, User> {
        val source = UserPageKeyedDataSource(page, limit, dataSource)
        sourceLiveData.postValue(source)
        return source
    }
}