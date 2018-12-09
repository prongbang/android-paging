package com.prongbang.paging.feature.user.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.prongbang.paging.feature.user.domain.GetUserByPageUseCase
import kotlinx.coroutines.*

class UserViewModel(
    private val useCase: GetUserByPageUseCase
) : ViewModel() {

    private val page = MutableLiveData<Int>()

    private val results = Transformations.map(page) { page ->
        runBlocking {
            useCase.getUserByPaged(page, 10)
        }
    }

    val users = Transformations.switchMap(results) { it.pagedList }
    val networkState = Transformations.switchMap(results) { it.networkState }
    val refreshState = Transformations.switchMap(results) { it.refreshState }

    private var job: Job? = null

    fun getUserByPage(pageSize: Int) {
        page.value = pageSize
    }

    fun refresh() {
        results.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = results?.value
        listing?.retry?.invoke()
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
