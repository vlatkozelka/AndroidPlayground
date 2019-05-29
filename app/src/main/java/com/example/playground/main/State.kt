package com.example.playground.main

import com.example.playground.Model.Doctor
import com.example.playground.services.base.RequestError
import com.example.playground.utils.Result
import com.zippyyum.subtemp.rxfeedback.LoadState
import org.notests.rxfeedback.Optional

/**
 * Created by Ali on 5/29/2019.
 */
data class State(
    //check if user is signed in
    var authentication: LoadState<Doctor> = LoadState.IsLoading(),


    //login page state
    var loginID: String = "",
    var checkID: LoadState<Result<Unit, RequestError>> = LoadState.Initial(),


    //Navigation
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
