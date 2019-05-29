package com.example.playground.utils.rx

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.zippyyum.subtemp.rxfeedback.getString
import io.reactivex.Observable
import org.notests.rxfeedback.Optional
import org.notests.sharedsequence.*
import com.example.playground.R

fun <T, U> Driver<T>.mapDistinct(transform: (T) -> U): Driver<U> {
    return this.map(transform).distinctUntilChanged()
}

fun <U> Observable<U>.asSignalElement(onError: (Throwable) -> U): Signal<U> {
    return asSignal { Signal.just(onError(it)) }
}

fun <U> Driver<Optional<U>>.collectNotNull(): Driver<U> {
    return filter { it is Optional.Some }.map { (it as Optional.Some).data }
}

fun <T, U> Driver<T>.collectNotNull(transform: (T) -> Optional<U>): Driver<U> {
    return this.map(transform).collectNotNull()
}

fun <U> Observable<Optional<U>>.collectNotNull(): Observable<U> {
    return filter { it is Optional.Some }.map { (it as Optional.Some).data }
}

fun <U> Observable<U>.asSignalLogFailure(): Signal<U> {
    return asSignal {
        Log.d("SignalError", "Non-fatal exception: ${it.stackTrace.joinToString("\n")}")
        Signal.empty()
    }
}

fun <State, Query, Event> reactSafely(
    query: (State) -> Optional<Query>,
    areEqual: (Query, Query) -> Boolean = { lhs, rhs -> lhs == rhs },
    effects: (Query) -> Signal<Event>
): (Driver<State>) -> Signal<Event> =
    { state ->
        state.map(query)
            .distinctUntilChanged { lhs, rhs ->
                when (lhs) {
                    is Optional.Some<Query> -> {
                        when (rhs) {
                            is Optional.Some<Query> -> areEqual(lhs.data, rhs.data)
                            is Optional.None<Query> -> false
                        }
                    }
                    is Optional.None<Query> -> {
                        when (rhs) {
                            is Optional.Some<Query> -> false
                            is Optional.None<Query> -> true
                        }
                    }
                }
            }.switchMapSignal { control: Optional<Query> ->
                if (control !is Optional.Some<Query>) {
                    return@switchMapSignal Signal.empty<Event>()
                }

                return@switchMapSignal effects(control.data)
                    .asObservable()
                    .observeOn(Signal.scheduler)
                    .subscribeOn(Signal.scheduler)
                    .asSignal<Event> { Signal.empty() }
            }
    }

fun <U> Observable<U>.asDriverLogFailure(): Driver<U> {
    return asDriver {
        Log.d("SignalError", "Non-fatal exception: ${it.stackTrace.joinToString("\n")}")
        Driver.empty()
    }
}

fun enterStringValue(
    activity: Activity,
    title: String,
    message: String? = null,
    customConfiguration: ((EditText) -> Unit)? = null
): Observable<Optional<String>> {
    return Observable.create<Optional<String>> { emitter ->
        val alertBuilder = AlertDialog.Builder(activity)

        val promptTextView = TextView(activity)
        val promptLayoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        promptLayoutParams.gravity = Gravity.CENTER
        if (!message.isNullOrEmpty()) {
            promptTextView.text = message
            promptLayoutParams.bottomMargin = 50
        }

        val editText = EditText(activity)
        customConfiguration?.invoke(editText)
        editText.setSingleLine(true)
        editText.maxLines = 1
        editText.setLines(1)


        val layout = LinearLayout(activity)
        val params =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = params
        layout.setPadding(50, 50, 50, 0)

        if (!message.isNullOrEmpty()) {
            layout.addView(promptTextView)
        }

        layout.addView(editText)

        alertBuilder.setView(layout)
        alertBuilder.setTitle(title)
        alertBuilder.setCancelable(false)

        alertBuilder.setPositiveButton(getString(R.string.done)) { _, _ ->
            if (editText.text.toString().isEmpty()) {
                emitter.onNext(Optional.None())
            } else {
                emitter.onNext(Optional.Some(editText.text.toString()))

            }
        }

        alertBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
            emitter.onNext(Optional.None())
        }

        val alertDialog = alertBuilder.create()
        if (!activity.isFinishing) {
            editText.requestFocus()
            alertDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            alertDialog.show()
        }
        emitter.setCancellable { alertDialog.hide() }
    }
}