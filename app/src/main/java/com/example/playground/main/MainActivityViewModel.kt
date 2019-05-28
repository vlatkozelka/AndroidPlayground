package com.example.playground.main

/**
 * Created by Ali on 5/29/2019.
 */

val State.greetingText: String
    get() {
        val name = this.name
        return if (name.isEmpty()) {
            "Bye!"
        } else {
            "Hello $name!"
        }

    }