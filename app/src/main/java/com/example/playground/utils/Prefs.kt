package com.example.playground.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.playground.App

/**
 * Created by Ali on 5/29/2019.
 */

object Prefs {


    private fun getPrefs(): SharedPreferences {
        return App.context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    fun setId(id: String) {
        getPrefs().edit().putString("id", id).apply()
    }

    fun getId(): String? {
        return getPrefs().getString("id", null)
    }

}