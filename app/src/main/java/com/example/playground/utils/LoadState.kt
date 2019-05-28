package com.zippyyum.subtemp.rxfeedback


import com.example.playground.services.base.RequestError
import com.example.playground.utils.Result

sealed class LoadState<T> {
    class Initial<T> : LoadState<T>()
    class IsLoading<T> : LoadState<T>()
    data class Loaded<T>(val data: T) : LoadState<T>()
    data class Error<T>(val error: RequestError) : LoadState<T>()

    fun reduce(result: Result<T, RequestError>): LoadState<T> {
        return when (result) {
            is Result.Success -> LoadState.Loaded(result.obj)
            is Result.Failure -> LoadState.Error(result.error)
        }
    }
}
