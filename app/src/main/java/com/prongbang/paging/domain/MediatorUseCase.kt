package com.prongbang.paging.domain

import androidx.lifecycle.MediatorLiveData
import com.prongbang.paging.model.Results

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result<R>].
 * Handling an exception (emit [Results.ServerError] to the result) is the subclasses's responsibility.
 */
abstract class MediatorUseCase<in P, R> {
    protected val result = MediatorLiveData<Results<R>>()

    // Make this as open so that mock instances can mock this method
    open fun observe(): MediatorLiveData<Results<R>> {
        return result
    }

    abstract fun execute(parameters: P)
}