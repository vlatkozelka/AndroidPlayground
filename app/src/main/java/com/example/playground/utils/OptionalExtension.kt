package com.example.playground.utils

import org.notests.rxfeedback.Optional

/**
 * Created by Nour on 11/30/2018.
 */
val <T> T?.asOptional: Optional<T>
    get() {
        return if (this != null)
            Optional.Some(this)
        else Optional.None()
    }

val <T> Optional<T>.valueOrNull: T?
get() {
    return when (this) {
        is Optional.Some -> this.data
        is Optional.None -> null
    }
}
