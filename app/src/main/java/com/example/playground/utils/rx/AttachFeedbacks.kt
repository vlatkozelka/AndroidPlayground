package com.example.playground.utils.rx

import android.util.Log
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject
import org.notests.sharedsequence.Driver
import org.notests.sharedsequence.Signal
import org.notests.sharedsequence.merge

/**
 * Created by Ali on 5/29/2019.
 */


/**
 * Attach feedbacks to an already running system
 */
fun <State, Event> attachFeedbacks(
    stateDriver: Driver<State>,
    eventsSubject: Subject<Event>,
    vararg feedbacks: (Driver<State>) -> Signal<Event>
): Disposable {
    return Signal.merge(
        feedbacks.map { feedback ->
            feedback(stateDriver)
        })
        .asObservable()
        .doOnError {
            Log.d("SignalError", "errorReporting: ${it.stackTrace.joinToString("\n")}")
        }
        .subscribe { event ->
            eventsSubject.onNext(event)
        }
}
