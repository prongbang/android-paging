package com.prongbang.paging.utils

import com.prongbang.paging.model.Results
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

suspend fun <T> safeApiCall(
    request: Deferred<Response<T>>
): Results<T> {
    try {
        val response = request.await()

        return if (response.isSuccessful) {
            if (response.body() != null) {
                Results.Success(response.body()!!)
            } else {
                Results.IsEmpty
            }
        } else {
            when (response.code()) {
                in 100..199 -> {
                    Results.Informational
                }
                in 301..399 -> {
                    Results.Redirection(Throwable(response.message()))
                }
                401 -> {
                    Results.Unauthorized
                }
                in 400..499 -> {
                    Results.ClientError(Throwable(response.message()))
                }
                in 500..599 -> {
                    Results.ServerError(Throwable(response.message()))
                }
                else -> {
                    Results.OtherError(Throwable(response.message()))
                }
            }
        }
    } catch (e: Exception) {
        return if (e is TimeoutException || e is SocketTimeoutException) {
            Results.Timeout
        } else {
            Results.OtherError(e)
        }
    }
}