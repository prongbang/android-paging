package com.prongbang.paging.feature.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prongbang.paging.feature.user.domain.GetUserByPageUseCase

class UserViewModelFactory(
    private val useCase: GetUserByPageUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}