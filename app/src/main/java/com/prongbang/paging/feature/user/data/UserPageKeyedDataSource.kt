package com.prongbang.paging.feature.user.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.prongbang.paging.feature.user.model.NetworkState
import com.prongbang.paging.feature.user.model.User
import com.prongbang.paging.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserPageKeyedDataSource(
    private val page: Int,
    private val limit: Int,
    private val dataSource: UserRemoteDataSource
) : PageKeyedDataSource<Int, User>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()
    val initialLoading = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            GlobalScope.launch(Dispatchers.IO) {
                it.invoke()
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        GlobalScope.launch(Dispatchers.IO) {
            initialLoading.postValue(NetworkState.LOADING)
            networkState.postValue(NetworkState.LOADING)

            val result = dataSource.getUserByPaged(page, limit)
            when (result) {
                is Results.Success -> {
                    retry = null
                    callback.onResult(result.data, null, page + 1)
                    initialLoading.postValue(NetworkState.LOADED)
                    networkState.postValue(NetworkState.LOADED)
                }
                is Results.ServerError -> {
                    loadInitialRetry(params, callback)
                    onError(NetworkState.serverError(result.exception?.message))
                }
                is Results.OtherError -> {
                    loadInitialRetry(params, callback)
                    onError(NetworkState.otherError(result.exception?.message))
                }
                is Results.ClientError -> {
                    loadInitialRetry(params, callback)
                    onError(NetworkState.clientError(result.exception?.message))
                }
                is Results.IsEmpty -> {
                    loadInitialRetry(params, callback)
                    onError(NetworkState.EMPTY)
                }
                is Results.Timeout -> {
                    loadInitialRetry(params, callback)
                    onError(NetworkState.TIMEOUT)
                }
                is Results.Unauthorized -> {
                    loadInitialRetry(params, callback)
                    onError(NetworkState.UNAUTHORIZED)
                }
            }
        }
    }

    private fun loadInitialRetry(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        retry = {
            loadInitial(params, callback)
        }
    }

    private fun onError(state: NetworkState) {
        initialLoading.postValue(NetworkState.LOADED)
        networkState.postValue(state)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        GlobalScope.launch(Dispatchers.IO) {
            initialLoading.postValue(NetworkState.LOADING)
            networkState.postValue(NetworkState.LOADING)

            val result = dataSource.getUserByPaged(params.key, limit)
            when (result) {
                is Results.Success -> {
                    callback.onResult(result.data, params.key + 1)
                    initialLoading.postValue(NetworkState.LOADED)
                    networkState.postValue(NetworkState.LOADED)
                }
                is Results.ServerError -> {
                    loadAfterRetry(params, callback)
                    onError(NetworkState.serverError(result.exception?.message))
                }
                is Results.OtherError -> {
                    loadAfterRetry(params, callback)
                    onError(NetworkState.otherError(result.exception?.message))
                }
                is Results.ClientError -> {
                    loadAfterRetry(params, callback)
                    onError(NetworkState.clientError(result.exception?.message))
                }
                is Results.IsEmpty -> {
                    loadAfterRetry(params, callback)
                    onError(NetworkState.EMPTY)
                }
                is Results.Timeout -> {
                    loadAfterRetry(params, callback)
                    onError(NetworkState.TIMEOUT)
                }
                is Results.Unauthorized -> {
                    loadAfterRetry(params, callback)
                    onError(NetworkState.UNAUTHORIZED)
                }
            }
        }
    }

    private fun loadAfterRetry(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, User>
    ) {
        retry = {
            loadAfter(params, callback)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        // ignored, since we only ever append to our initial load
    }

}