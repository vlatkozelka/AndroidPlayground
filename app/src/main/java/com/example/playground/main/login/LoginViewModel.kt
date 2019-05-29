package com.example.playground.main.login

import android.view.View
import com.example.playground.main.State
import com.zippyyum.subtemp.rxfeedback.LoadState

/**
 * Created by Ali on 5/29/2019.
 */

val State.progressBarVisibility: Int
    get() {
        return if (loginState is LoadState.IsLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

val State.loginErrorVisibiltiy: Int
    get() {
        return if (loginState is LoadState.Error) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

val State.loginButtonEnabled: Boolean
    get() {
        val loginID = this.loginID
        return loginID.isNotEmpty() && loginState !is LoadState.IsLoading
    }