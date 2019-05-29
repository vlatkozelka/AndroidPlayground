package com.example.playground.main

import android.util.Log
import com.example.playground.Model.Patient
import com.example.playground.utils.Prefs
import com.example.playground.utils.Result
import com.zippyyum.subtemp.rxfeedback.LoadState
import org.notests.rxfeedback.Optional

/**
 * Created by Ali on 5/29/2019.
 */

object MainStateReducer {


    fun reduce(state: State, event: Event): State {
        Log.d("Event", event.toString())
        return when (event) {
            is Event.NavigatedToFragment -> state.copy().apply {
                route = Optional.None()
                currentFragmentTag = event.tag
            }

            is Event.ClickedLogin -> state.copy().apply {
                loginState = LoadState.IsLoading()
            }

            is Event.GotAutoAuthenticationResult -> state.copy().apply {
                when (event.result) {
                    is Result.Success -> {
                        authentication = LoadState.Loaded(event.result.obj)
                        loginState = LoadState.Loaded(event.result.obj)
                        Prefs.setId(event.result.obj.id)
                        route = Optional.Some(State.Route.Patients)
                    }
                    is Result.Failure -> {
                        authentication = LoadState.Error(event.result.error)
                        route = Optional.Some(State.Route.Login)
                        Prefs.setId("")
                    }
                }
            }

            is Event.GotoAuthenticationResult -> state.copy().apply {
                when (event.result) {
                    is Result.Success -> {
                        authentication = LoadState.Loaded(event.result.obj)
                        loginState = LoadState.Loaded(event.result.obj)
                        Prefs.setId(event.result.obj.id)
                        route = Optional.Some(State.Route.Patients)
                    }
                    is Result.Failure -> {
                        authentication = LoadState.Error(event.result.error)
                        loginState = LoadState.Error(event.result.error)
                        Prefs.setId("")
                    }
                }
            }

            is Event.EnteredId -> state.copy().apply {
                loginID = event.id
            }

            Event.ClickedAddPatient -> state.copy().apply {
                route = Optional.Some(State.Route.AddPatient)
            }

            Event.ClickedSubmitAddPatient -> state.copy().apply {
                addPatientState = LoadState.IsLoading()
            }

            is Event.PatientNameChanged -> state.copy().apply {
                addPatientName = event.name
            }

            is Event.PatientNumberChanged -> state.copy().apply {
                addPatientNumber = event.number
            }

            is Event.PatientDeviceSerialChanged -> state.copy().apply {
                addPatientDeviceSerial = event.serial
            }

            is Event.GotAddPatientResult -> state.copy().apply {
                val loginState = this.loginState
                if (loginState is LoadState.Loaded && event.result is Result.Success) {
                    loginState.data.patients.add(event.result.obj)
                    route = Optional.Some(State.Route.Patients)
                }
            }

        }
    }


}