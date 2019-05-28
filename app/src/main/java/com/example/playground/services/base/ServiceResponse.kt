package com.example.playground.services.base

import org.json.JSONObject
import com.example.playground.utils.Result


/**
 * Created by ali on 5/15/2018.
 */

data class ServiceResponse(
    var status: String,
    var result: Result<JSONObject, RequestError>
) {
    companion object {
        const val STATUS_FAILURE = "failure"
        const val STATUS_SUCCESS = "success"
    }

}