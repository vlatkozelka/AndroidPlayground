package com.example.playground.utils

/**
 * Created by ali on 5/15/2018.
 * The purpose of this class is to provide a map function that transforms the result only if it's not
 * an error, and leave the object intact in case it's an error
 *
 * This is to make it easier to propagate an error back to calling methods
 *
 * Without this structure, we would have to check the type of the result each time it is accessed.
 * See the demo below
 *
 */
sealed class Result<out T, E> {

    data class Success<out T, E>(val obj: T) : Result<T, E>()
    data class Failure<out T, E>(val error: E) : Result<T, E>()

    /**
     * Provide a transformation for a success result. And leave the error intact,encapsulated in
     * a new Result object
     */
    fun <U> map(transform: (T) -> U): Result<U, E> {
        return when (this) {
            is Success -> {
                Success(transform(this.obj))
            }

            is Failure -> {
                Failure(this.error)
            }
        }
    }

    /**
     * Call this when there's a potential error in the transformation
     * For example when parsing a successful result, but the parsing itself may produce an error
     */
    fun <U> flatMap(transform: (T) -> Result<U, E>): Result<U, E> {
        return when (this) {
            is Success -> {
                transform(this.obj)
            }
            is Failure -> {
                Failure(this.error)
            }
        }
    }

    fun handle(success: (T) -> Unit, failure: (E) -> Unit) {
        when (this) {
            is Success -> success(this.obj)
            is Failure -> failure(this.error)
        }
    }


}
