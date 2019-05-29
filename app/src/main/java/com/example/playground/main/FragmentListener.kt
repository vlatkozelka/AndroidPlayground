package com.example.playground.main

import io.reactivex.disposables.Disposable
import org.notests.sharedsequence.Driver
import org.notests.sharedsequence.Signal

/**
 * Created by Ali on 5/29/2019.
 */
interface FragmentListener {

    fun onFragmentResumed(tag: String)

    fun onAttachFeedbacks(vararg feedbacks: (Driver<State>) -> Signal<Event>): Disposable?

}