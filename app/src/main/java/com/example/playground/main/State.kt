package com.example.playground.main

import com.example.playground.Model.Doctor
import com.example.playground.main.MainActivity.Companion.TAG_SPLASH_FRAGMENT
import com.example.playground.services.base.RequestError
import com.example.playground.utils.Result
import com.zippyyum.subtemp.rxfeedback.LoadState
import okhttp3.Route
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
    }

}
