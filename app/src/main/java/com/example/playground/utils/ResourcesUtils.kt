package com.zippyyum.subtemp.rxfeedback

import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.playground.App

fun getString(@StringRes id: Int): String {
    val context = App.context
    return context.getString(id)
}

fun getString(@StringRes id: Int, vararg args: Any): String {
    val context = App.context
    return context.getString(id, *args)
}

fun getColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(App.context, id)
}

fun getAnimation(id: Int?): Animation? {
    return if (id == -1 || id == null) {
        null
    } else {
        AnimationUtils.loadAnimation(App.context, id)
    }

}

fun getDimen(@DimenRes id: Int): Float {
    val context = App.context
    return context.resources.getDimension(id)
}