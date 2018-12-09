package com.prongbang.paging.feature.user.di

import com.prongbang.paging.feature.user.api.UserService
import com.prongbang.paging.feature.user.data.DefaultUserRepository
import com.prongbang.paging.feature.user.data.UserRemoteDataSource
import com.prongbang.paging.feature.user.domain.GetUserByPageUseCase
import com.prongbang.paging.feature.user.ui.UserViewModelFactory
import com.prongbang.paging.utils.ServiceGenerator

object Injection {

    fun provideBaseURL() = "http://172.20.10.3:3000/"

    // Provide API Service
    fun provideUserService() = ServiceGenerator.create(provideBaseURL(), UserService::class.java)

    // Provide DataSource
    fun provideUserRemoteDataSource() = UserRemoteDataSource(provideUserService())

    // Provide Repository
    fun provideUserRepository() = DefaultUserRepository(provideUserRemoteDataSource())

    // Provide UseCase
    fun provideGetUserByPageUseCase() = GetUserByPageUseCase(provideUserRepository())

    // Provide Factory
    fun provideUserViewModelFactory() = UserViewModelFactory(provideGetUserByPageUseCase())
}