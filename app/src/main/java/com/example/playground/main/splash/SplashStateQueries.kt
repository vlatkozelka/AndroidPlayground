package com.example.playground.main.splash

import com.example.playground.main.State
import com.example.playground.utils.Prefs
import com.zippyyum.subtemp.rxfeedback.LoadState
import org.notests.rxfeedback.Optional

/**
 * Created by Ali on 5/29/2019.
 */


fun State.authenticate(): Optional<String> {
    val authentication = this.authentication
    return if (authentication is LoadState.IsLoading) {
        return Optional.Some(Prefs.getId() ?: "")
    } else {
        Optional.None()
    }
}