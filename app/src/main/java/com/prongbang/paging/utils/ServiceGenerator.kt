package com.prongbang.paging.utils

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object ServiceGenerator {

    fun <T> create(baseUrl: String, clazz: Class<T>): T {

        val logger = HttpLoggingInterceptor()
        try {
            logger.level = HttpLoggingInterceptor.Level.BASIC
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "network")
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(clazz)
    }

}