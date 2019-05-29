package com.example.playground.main.patients

import android.view.View
import com.example.playground.main.State
import com.zippyyum.subtemp.rxfeedback.LoadState

/**
 * Created by Ali on 5/29/2019.
 */


data class PatientViewModel(
        val name: String,
        val image: String = ""
)

val State.noPatientsTextVisibility: Int
    get() {
        val loginState = loginState
        return if (loginState is LoadState.Loaded && loginState.data.patients.isNotEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

val State.patientsViewModels: List<PatientViewModel>
    get() {
        val loginState = loginState
        return if (loginState is LoadState.Loaded) {
            loginState.data.patients.map { PatientViewModel(it.name) }
        } else {
            listOf<PatientViewModel>()
        }
    }