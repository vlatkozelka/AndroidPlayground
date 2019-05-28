package com.example.playground.utils.rx

import org.notests.rxfeedback.Optional
import org.notests.sharedsequence.*

fun <State, Query, Event> customFeedback(
        query: (State) -> Optional<Query>,
        effects: (Query, Driver<State>) -> Signal<Event>
): (Driver<State>) -> Signal<Event> =
        { state ->
            state.map(query)
                    .distinctUntilChanged { lhs, rhs ->
                        when (lhs) {
                            is Optional.Some<Query> -> {
                                when (rhs) {
                                    is Optional.Some<Query> -> lhs.data == rhs.data
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
                    }
                    .switchMapSignal { control: Optional<Query> ->
                        if (control !is Optional.Some<Query>) {
                            return@switchMapSignal Signal.empty<Event>()
                        }
                        effects(control.data, state)
                    }

        }
