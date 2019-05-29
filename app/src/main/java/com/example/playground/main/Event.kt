package com.example.playground.main

/**
 * Created by Ali on 5/29/2019.
 */
sealed class Event {
    data class ClickedLogin(val id: String) : Event()
}
