package com.example.playground.utils

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

fun JSONArray.jsonObjects(): List<JSONObject> {
    return (0 until this.length()).map {
        getJSONObject(it)
    }
}

inline fun <reified T> JSONArray.gsonParseArray(): List<T> {
    val gson = Gson()
    return jsonObjects().map {
        gson.fromJson(it.toString(), T::class.java)
    }
}
