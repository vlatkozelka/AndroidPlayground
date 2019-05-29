package com.example.playground.services.base

import com.example.playground.utils.Result
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

data class RequestError(
    val code: Int,
    val message: String
) {


    //todo fix error code and localization
    companion object {
        @JvmStatic
        fun parsingError(): RequestError {
            return RequestError(666, "error parsing response")
        }

        fun notFoundError(): RequestError{
            return RequestError(404, "Not found")
        }

        fun authenticationError(): RequestError{
            return RequestError(401, "Not authenticated")
        }

        @JvmStatic
        fun <T> arrayParsingError(t: Class<T>): RequestError {//todo fix error code and localization
            return RequestError(
                667,
                "error parsing response: Error converting array of type ${t.name}"
            )
        }
    }
}



/**
 * Converts a JSONObject to an Object of Class T.
 * Replaces the error with another error in case a parsing error happens
 */
fun <T> Result<JSONObject, RequestError>.convert(t: Class<T>): Result<T, RequestError> {
    return flatMap {
        try {
            Result.Success<T, RequestError>(
                Gson().fromJson(it.toString(), t)
            )
        } catch (ex: JSONException) {
            Result.Failure<T, RequestError>(
                RequestError(
                    666,
                    "Unparsable GSON response error ${t.name}"
                )
            )//todo fix error code and localization
        }
    }
}
//send key of tenant

fun <T> Result<JSONObject, RequestError>.convertArray(
    key: String,
    t: Class<T>
): Result<ArrayList<T>, RequestError> {
    return flatMap { json ->
        try {
            val jsonArray = json.optJSONArray(key)
            if (jsonArray == null) {
                Result.Failure<ArrayList<T>, RequestError>(
                    RequestError.arrayParsingError(t)
                )
            } else {
                val array = ArrayList<T>(jsonArray.length())
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    array.add(Gson().fromJson(jsonObject.toString(), t))
                }
                Result.Success<ArrayList<T>, RequestError>(
                    array
                )
            }

        } catch (ex: JSONException) {
            Result.Failure<ArrayList<T>, RequestError>(
                RequestError.arrayParsingError(t)
            )
        }
    }
}
