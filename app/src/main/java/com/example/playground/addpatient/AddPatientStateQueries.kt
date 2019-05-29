package com.example.playground.addpatient

import com.example.playground.Model.Patient
import com.example.playground.Model.Report
import com.example.playground.main.State
import com.zippyyum.subtemp.rxfeedback.LoadState
import org.notests.rxfeedback.Optional

/**
 * Created by Ali on 5/29/2019.
 */


fun State.addPatient(): Optional<Patient> {
    val addPatientState = this.addPatientState
    return if (addPatientState is LoadState.IsLoading) {
        Optional.Some(
                Patient(
                        addPatientName,
                        Report.getReports(),
                        addPatientDeviceSerial,
                        addPatientNumber
                )
        )
    } else {
        Optional.None()
    }
}