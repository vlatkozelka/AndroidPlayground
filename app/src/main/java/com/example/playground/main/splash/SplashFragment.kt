package com.example.playground.main.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.model.Doctor
import com.example.playground.R
import com.example.playground.main.BaseFragment
import com.example.playground.main.Event
import com.example.playground.main.Feedback
import com.example.playground.main.MainActivity.Companion.TAG_SPLASH_FRAGMENT
import com.example.playground.main.State
import com.example.playground.services.base.RequestError
import com.example.playground.utils.Result
import com.example.playground.utils.rx.asSignalLogFailure
import com.example.playground.utils.rx.reactSafely
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by Ali on 5/29/2019.
 */

class SplashFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        return view
    }


    override fun onResume() {
        super.onResume()
        listener?.onFragmentResumed(TAG_SPLASH_FRAGMENT)
        val disposable = listener?.onAttachFeedbacks(
            authenticate()
        )
        if(disposable!=null){
            disposables.add(disposable)
        }
    }


    fun authenticate(): Feedback {
        return reactSafely<State, String, Event>(
            query = {
                it.authenticate()
            },
            effects = { id ->
                //call the backend
                Observable.timer(2, TimeUnit.SECONDS).map<Event> {
                    val doc = Doctor.finDoctorById(id)
                    if (doc != null) {
                        Event.GotAutoAuthenticationResult(Result.Success(doc))
                    } else {
                        Event.GotAutoAuthenticationResult(Result.Failure(RequestError.authenticationError()))
                    }
                }.asSignalLogFailure()
            }
        )
    }
}
