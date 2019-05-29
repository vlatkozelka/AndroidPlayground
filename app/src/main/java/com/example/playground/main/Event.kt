package com.example.playground.main

import com.example.playground.model.Doctor
import com.example.playground.model.Patient
import com.example.playground.services.base.RequestError
import com.example.playground.utils.Result

/**
 * Created by Ali on 5/29/2019.
 */
sealed class Event {
    //splash screen
    data class GotAutoAuthenticationResult(val result: Result<Doctor, RequestError>) : Event()

    //login

    data class GotoAuthenticationResult(val result: Result<Doctor, RequestError>) : Event()
    object ClickedLogin : Event()
    data class EnteredId(val id: String) : Event()

    //Patients

    object ClickedAddPatient : Event()
    data class ClickedOnPatient(val position: Int) : Event()

    //Add Patients
    object ClickedSubmitAddPatient : Event()

    data class PatientNameChanged(val name: String) : Event()
    data class PatientNumberChanged(val number: String) : Event()
    data class PatientDeviceSerialChanged(val serial: String) : Event()
    data class GotAddPatientResult(val result: Result<Patient, RequestError>) : Event()

    //Navigation

    data class NavigatedToFragment(val tag: String) : Event()
    object BackPressed: Event()
}
