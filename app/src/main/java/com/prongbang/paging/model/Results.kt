package com.prongbang.paging.model

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Results<out R> {

    object Loading : Results<Nothing>()
    object IsEmpty : Results<Nothing>()
    object Unauthorized : Results<Nothing>()
    object Informational : Results<Nothing>()
    object Timeout : Results<Nothing>()
    data class Success<out T>(val data: T) : Results<T>()
    data class Redirection(val exception: Throwable?) : Results<Nothing>()
    data class ClientError(val exception: Throwable?) : Results<Nothing>()
    data class ServerError(val exception: Throwable?) : Results<Nothing>()
    data class OtherError(val exception: Throwable?) : Results<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is IsEmpty -> "IsEmpty"
            is Timeout -> "Timeout"
            is Unauthorized -> "Timeout"
            is Informational -> "Informational"
            is Success<*> -> "Success[data=$data]"
            is Redirection -> "Redirection[exception=$exception]"
            is ClientError -> "ClientError[exception=$exception]"
            is ServerError -> "ServerError[exception=$exception]"
            is OtherError -> "OtherError[exception=$exception]"
        }
    }
}

/**
 * `true` if [Results] is of type [Results.Success] & holds non-null [Results.Success.data].
 */
val Results<*>.succeeded get() = this is Results.Success && data != null