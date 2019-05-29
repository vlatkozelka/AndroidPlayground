package com.example.playground.main

import com.example.playground.Model.Doctor
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


    data class NavigatedToFragment(val tag: String) : Event()
}
