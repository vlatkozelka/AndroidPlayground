package com.example.playground.main.login

import com.example.playground.main.State
import com.zippyyum.subtemp.rxfeedback.LoadState
import org.notests.rxfeedback.Optional

/**
 * Created by Ali on 5/29/2019.
 */

fun State.login(): Optional<String> {
    val loginState = this.loginState
    return if (loginState is LoadState.IsLoading) {
        Optional.Some(loginID)
    } else {
        Optional.None()
    }
}