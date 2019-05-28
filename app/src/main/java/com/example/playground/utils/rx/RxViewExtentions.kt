package com.zippyyum.subtemp.utilities

import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.focusChanges
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import org.notests.sharedsequence.*


//This files contains extensions to reduce Rx Views boilerplate code


fun <Event> EditText.watchChanges(mapperFunction: (String) -> Event): Signal<Event> {
    return this.textChanges()
            .skip(1)
            .map { it.toString() }
            .distinctUntilChanged()
            .asSignal(Signal.empty())
            .map(mapperFunction)
}

fun <Event> View.watchClicks(mapperFunction: (Unit?) -> Event): Signal<Event> {
    return this.clicks()
            .asSignal(Signal.empty())
            .map(mapperFunction)
}

fun <Event> RadioButton.watchSelected(mapperFunction: (Unit) -> Event): Signal<Event> {
    return this.checkedChanges()
            .asSignal(Signal.empty())
            .switchMapSignal { isSelected ->
                if (isSelected) {
                    Signal(Observable.just(mapperFunction(Unit)))
                } else {
                    Signal.empty()
                }

            }
}


fun <Event> View.watchFocusChange(mapperFunction: (Boolean) -> Event): Signal<Event> {
    return this.focusChanges()
            .distinctUntilChanged()
            .asSignal(Signal.empty())
            .switchMapSignal { isFocused ->
                Signal(Observable.just(mapperFunction(isFocused)))
            }
}


enum class HorizontalSwipeDirection {
    Left, Right
}

fun View.horizontalSwipeEvents(): Signal<HorizontalSwipeDirection> {
    return Observable.create<HorizontalSwipeDirection> { emitter ->
        this.setOnTouchListener(object : View.OnTouchListener {
            private val MIN_DISTANCE = (100 * resources.displayMetrics.density + 0.5f).toInt()
            private var x1: Float = 0.toFloat()
            private var x2: Float = 0.toFloat()

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> x1 = event.x
                    MotionEvent.ACTION_UP -> {
                        x2 = event.x
                        val deltaX = x2 - x1
                        if (deltaX < -MIN_DISTANCE) {
                            emitter.onNext(HorizontalSwipeDirection.Left)
                        }
                        if (deltaX > MIN_DISTANCE) {
                            emitter.onNext(HorizontalSwipeDirection.Right)
                        }
                    }
                }
                return false
            }
        })
        emitter.setCancellable { this.setOnTouchListener(null) }
    }.asSignal(Signal.empty())
}
