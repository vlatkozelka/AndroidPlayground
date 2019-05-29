package com.example.playground.addpatient

import android.view.View
import com.example.playground.main.State
import com.zippyyum.subtemp.rxfeedback.LoadState

/**
 * Created by Ali on 5/29/2019.
 */

val State.addButtonEnabled: Boolean
    get() {
        val addPatientState = this.addPatientState
        return addPatientName.isNotEmpty() && addPatientDeviceSerial.isNotEmpty() && addPatientNumber.isNotEmpty() && addPatientState !is LoadState.IsLoading
    }

val State.addPatientProgressVisibility: Int
    get() {
        val addPatientState = this.addPatientState
        return if (addPatientState is LoadState.IsLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }