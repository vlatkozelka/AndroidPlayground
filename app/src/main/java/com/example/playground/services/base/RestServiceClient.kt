package com.example.playground.services.base

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


/**
 * This is the class to inherit from and define methods to call services.
 * It provides 2 methods, requestSync() and requestAsync(callback).
 * As their name suggest, the first requests a service and returns a response on the thread it
 * was called on. The second will do the request on a background thread, and return the result via
 * a callback method that is passed to it.
 * Created by ali on 5/15/2018.
 */

open class RestServiceClient(private val responseConverter: ResponseConverter,
                             protected val retrofit: Retrofit) {

    /**
     * Executes a service call on the thread it was launched on
     * @param call the call this method will execute
     * @return ServiceResponse
     */
    protected fun requestSync(call: Call<ResponseBody>): ServiceResponse {
        return try {
            val response = call.execute()
            val responseString: String
            responseString = if (response.isSuccessful) {
                response.body().string()
            } else {
                response.errorBody().string()
            }
            responseConverter.parseResponse(responseString)
        } catch (ex: Exception) {
            responseConverter.parseException(ex)
        }
    }


    /**
     * Executes a service call on a background thread. And returns a ServiceResponse via a
     * callback function passed to this method
     * @param call the call this method will execute
     * @param callback a callback function to handle the ServiceResponse
     */
    protected fun requestAsync(call: Call<ResponseBody>, callback: (ServiceResponse) -> Unit) {
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                t?.printStackTrace()
                callback(responseConverter.parseException(t))
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val responseString = if (response?.isSuccessful == true) {
                    response.body().string()
                } else {
                    //TODO replace this with a proper error, and with localization
                    response?.errorBody()?.string()
                            ?: """ {"status":"errors":[{"code":666,"message":"error parsing response"}]}"""
                }
                callback(responseConverter.parseResponse(responseString))
            }

        })
    }
}