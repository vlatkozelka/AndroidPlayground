package com.example.playground.main.patientprofile

import com.example.playground.main.State
import org.notests.rxfeedback.Optional

/**
 * Created by Ali on 5/29/2019.
 */

fun State.sign(): Optional<Int> {
    return addSignature
}