package com.prongbang.paging.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prongbang.paging.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Executes business logic synchronously or asynchronously using a [Coroutines].
 */
abstract class UseCase<in Params, Result> {

    /** Executes the use case asynchronously and places the [Results] in a MutableLiveData
     *
     * @param parameters the input parameters to run the use case with
     * @param result the MutableLiveData where the result is posted to
     *
     */
    suspend operator fun invoke(parameters: Params, result: MutableLiveData<Results<Result>>) {
        result.value = Results.Loading
        try {
            withContext(Dispatchers.Default) {
                try {
                    execute(parameters).let { useCaseResult ->
                        result.postValue(Results.Success(useCaseResult))
                    }
                } catch (e: Exception) {
                    result.postValue(Results.ServerError(e))
                }
            }
        } catch (e: Exception) {
            result.postValue(Results.ServerError(e))
        }
    }

    /** Executes the use case asynchronously and returns a [Results] in a new LiveData object.
     *
     * @return an observable [LiveData] with a [Results].
     *
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: Params): LiveData<Results<Result>> {
        val liveCallback: MutableLiveData<Results<Result>> = MutableLiveData()
        this(parameters, liveCallback)
        return liveCallback
    }

    /** Executes the use case synchronously  */
    fun executeNow(parameters: Params): Results<Result> {
        return try {
            Results.Success(execute(parameters))
        } catch (e: Exception) {
            Results.ServerError(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: Params): Result
}

suspend operator fun <R> UseCase<Unit, R>.invoke(): LiveData<Results<R>> = this(Unit)
suspend operator fun <R> UseCase<Unit, R>.invoke(result: MutableLiveData<Results<R>>) = this(Unit, result)