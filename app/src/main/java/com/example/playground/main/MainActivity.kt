package com.example.playground.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.playground.R
import com.example.playground.utils.rx.attachFeedbacks
import com.zippyyum.subtemp.utilities.watchChanges
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import org.notests.rxfeedback.Bindings
import org.notests.rxfeedback.bindSafe
import org.notests.rxfeedback.system
import org.notests.sharedsequence.*


typealias Feedback = (Driver<State>) -> Signal<Event>

class MainActivity : AppCompatActivity(), FragmentListener {

    val disposables = arrayListOf<Disposable>()
    val eventsSubject = PublishSubject.create<Event>()
    val eventsSignal = Signal(eventsSubject)
    var stateDriver: Driver<State>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()

        stateDriver = Driver.system(
            State(),
            MainStateReducer::reduce,
            bindUI()
        )
        stateDriver?.let { disposables.add(it.drive()) }
    }

    override fun onPause() {
        super.onPause()
        disposables.forEach { it.dispose() }
    }


    fun bindUI(): Feedback {
        return bindSafe<State, Event> { stateDriver ->
            Bindings.safe(
                subscriptions = listOf(
                ),
                events = listOf(
                    Signal.never()
                )
            )
        }
    }

    fun bindEventsSubject(): Feedback {
        return bindSafe<State, Event> { stateDriver ->
            Bindings.safe(
                subscriptions = listOf(
                    //no UI changes here
                ),
                events = listOf(
                    eventsSignal
                )
            )

        }
    }


    override fun onFragmentResumed() {

    }

    override fun onAttachFeedbacks(vararg feedbacks: (Driver<State>) -> Signal<Event>) {
        stateDriver?.let { driver ->
            attachFeedbacks(driver, eventsSubject, *feedbacks)
        }
    }


}
