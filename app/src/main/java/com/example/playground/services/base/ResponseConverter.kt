package com.example.playground.services.base

import com.example.playground.utils.Result

/**
 * Created by ali on 5/18/2018.
 */
abstract class ResponseConverter {


    /**
     * A response can still be a failure. This simply indicates that a response came back from the server.
     * The status field of ServiceResponse indicates if there's an error with the request
     */
    abstract fun parseResponse(responseString: String): ServiceResponse


    /**
     * Errors are thrown only when a network exception happens. e.g.: A connection error, a connection
     * reset, no internet available, etc...
     */
    fun parseException(throwable: Throwable?): ServiceResponse {
        val errors = mutableListOf<RequestError>()
        errors.add(RequestError(666, "Connection error"))
        return ServiceResponse(ServiceResponse.STATUS_FAILURE, Result.Failure(errors[0]))
    }


}