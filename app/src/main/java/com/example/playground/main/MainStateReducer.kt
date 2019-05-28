package com.example.playground.main

/**
 * Created by Ali on 5/29/2019.
 */

object MainStateReducer {


    fun reduce(state: State, event: Event): State {

        return when (event) {
            Event.TestBtnClicked -> state.copy().apply {

            }
            is Event.NameChanged -> state.copy().apply {
                this.name = event.name
            }
        }
    }

}