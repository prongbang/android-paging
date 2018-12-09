package com.prongbang.paging.feature.user.model

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    CLIENT_FAILED,
    TIMEOUT,
    UNAUTHORIZED,
    EMPTY,
    OTHER_FAILED,
    REDIRECTION,
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val msg: String? = null
) {

    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        val TIMEOUT = NetworkState(Status.TIMEOUT)
        val UNAUTHORIZED = NetworkState(Status.UNAUTHORIZED)
        val EMPTY = NetworkState(Status.EMPTY)
        val REDIRECTION = NetworkState(Status.REDIRECTION)

        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
        fun serverError(msg: String?) = NetworkState(Status.FAILED, msg)
        fun clientError(msg: String?) = NetworkState(Status.CLIENT_FAILED, msg)
        fun otherError(msg: String?) = NetworkState(Status.OTHER_FAILED, msg)
    }
}