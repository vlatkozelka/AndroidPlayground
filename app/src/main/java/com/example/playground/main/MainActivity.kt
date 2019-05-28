package com.example.playground.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.playground.R
import com.zippyyum.subtemp.utilities.watchChanges
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import org.notests.rxfeedback.Bindings
import org.notests.rxfeedback.bindSafe
import org.notests.rxfeedback.system
import org.notests.sharedsequence.*

data class State(
    var name: String = "World"
)

sealed class Event {
    object TestBtnClicked : Event()
    data class NameChanged(val name: String) : Event()
}


typealias Feedback = (Driver<State>) -> Signal<Event>

class MainActivity : AppCompatActivity() {


    val disposables = arrayListOf<Disposable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()

        val systemDisposable = Driver.system(
            State(),
            MainStateReducer::reduce,
            bindUI()
        )
            .drive()
        disposables.add(systemDisposable)
    }

    override fun onPause() {
        super.onPause()
        disposables.forEach { it.dispose() }
    }


    fun bindUI(): Feedback {
        return bindSafe<State, Event> { stateDriver ->
            Bindings.safe(
                subscriptions = listOf(
                    stateDriver.map { it.greetingText }.distinctUntilChanged().drive { txt_greeting.text = it }
                ),
                events = listOf(
                    edt_name.watchChanges<Event> { Event.NameChanged(it) }
                )
            )
        }
    }
}
