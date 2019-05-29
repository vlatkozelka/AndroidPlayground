package com.example.playground.main

import android.util.Log
import com.example.playground.R
import com.example.playground.utils.Prefs
import com.example.playground.utils.Result
import com.zippyyum.subtemp.rxfeedback.LoadState
import com.zippyyum.subtemp.rxfeedback.getString
import org.notests.rxfeedback.Optional

/**
 * Created by Ali on 5/29/2019.
 */

object MainStateReducer {
    fun reduce(state: State, event: Event): State {
        Log.d("ReduceEvent", event.toString())
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

            is Event.ClickedOnPatient -> state.copy().apply {
                val loginState = this.loginState
                if (loginState is LoadState.Loaded) {
                    currentPatient = Optional.Some(loginState.data.patients[event.position])
                    route = Optional.Some(State.Route.PatientProfile)
                }
            }

            is Event.BackPressed -> state.copy().apply {
                when (currentFragmentTag) {
                    MainActivity.TAG_LOGIN_FRAGMENT -> route = Optional.Some(State.Route.Quit)
                    MainActivity.TAG_PATIENTS_FRAGMENT -> route = Optional.Some(State.Route.Quit)
                    MainActivity.TAG_ADD_PATIENT_FRAGMENT -> route = Optional.Some(State.Route.Patients)
                    MainActivity.TAG_PATIENT_PROFILE_FRAGMENT -> route = Optional.Some(State.Route.Patients)
                }
            }

            is Event.ClickedViewReport -> state.copy().apply {
                //should be dynamic, but I don't have any PDFs
                route = Optional.Some(State.Route.ViewReport("http://www.africau.edu/images/default/sample.pdf"))
            }

            is Event.ClickedSignReport -> state.copy().apply {
                addSignature = Optional.Some(event.position)
            }


            Event.CanceledSignReport -> state.copy().apply {
                addSignature = Optional.None()
            }

            is Event.SignedReport -> state.copy().apply {

                val currentPatient = currentPatient
                val addSignature = this.addSignature
                if (currentPatient is Optional.Some && addSignature is Optional.Some) {
                    currentPatient.data.reports[addSignature.data].signature = event.comment
                }
                this.addSignature = Optional.None()
            }
        }
    }


}