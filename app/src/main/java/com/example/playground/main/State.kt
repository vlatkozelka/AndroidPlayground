package com.example.playground.main

import com.example.playground.model.Doctor
import com.example.playground.main.MainActivity.Companion.TAG_SPLASH_FRAGMENT
import com.example.playground.model.Patient
import com.zippyyum.subtemp.rxfeedback.LoadState
import org.notests.rxfeedback.Optional

/**
 * Created by Ali on 5/29/2019.
 */
data class State(
    //splash
    var authentication: LoadState<Doctor> = LoadState.IsLoading(),


    //login
    var loginID: String = "",
    var loginState: LoadState<Doctor> = LoadState.Initial(),

    //add patient

    var addPatientState: LoadState<Unit> = LoadState.Initial(),
    var addPatientName: String = "",
    var addPatientNumber: String = "",
    var addPatientDeviceSerial: String = "",


    //patient profile
    var currentPatient: Optional<Patient> = Optional.None(),
    var addSignature: Optional<Int> = Optional.None(),

    //Navigation
    var currentFragmentTag: String = TAG_SPLASH_FRAGMENT,
    var route: Optional<Route> = Optional.None()
) {


    sealed class Route {
        object Login : Route()
        object Patients : Route()
        object AddPatient : Route()
        object PatientProfile : Route()
        data class ViewReport(val report: String) : Route()
        object Quit : Route()
    }

}
