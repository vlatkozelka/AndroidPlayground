package com.example.playground.services.base

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 *
 *
 * Created by ali on 5/22/2018.
 */
open class HeadersProvider : Interceptor {


    protected val headers = HashMap<String, String>()


    fun addHeader(key: String, value: String): HeadersProvider {
        headers[key] = value
        return this
    }


    final override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response: okhttp3.Response
        val requestBuilder = original.newBuilder()

        val customHeaders = hashMapOf<String,String?>()

        //copy headers to custom headers
        headers.keys.forEach { key-> customHeaders[key] = headers[key] }

        val modifiedHeaders = modifyHeaders(original)

        //copy modified headers to custom headers
        modifiedHeaders.keys.forEach { key-> customHeaders[key] = modifiedHeaders[key] }

        //add custom headers to request
        customHeaders.forEach { header -> requestBuilder.header(header.key, header.value) }

        val request = requestBuilder
                .method(original.method(), original.body())
                .build()

        response = if (customHeaders.size > 0) {
            chain.proceed(request)
        } else {
            chain.proceed(original)
        }

        return response
    }

    open fun modifyHeaders(request: Request): HashMap<String, String?> {
        return hashMapOf()
    }


}