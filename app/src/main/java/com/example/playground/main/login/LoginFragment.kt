package com.example.playground.main.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.Model.Doctor
import com.example.playground.R
import com.example.playground.main.BaseFragment
import com.example.playground.main.Event
import com.example.playground.main.Feedback
import com.example.playground.main.MainActivity.Companion.TAG_LOGIN_FRAGMENT
import com.example.playground.main.State
import com.example.playground.services.base.RequestError
import com.example.playground.utils.Result
import com.example.playground.utils.rx.asSignalLogFailure
import com.example.playground.utils.rx.reactSafely
import com.zippyyum.subtemp.utilities.watchChanges
import com.zippyyum.subtemp.utilities.watchClicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_login.*
import org.notests.rxfeedback.Bindings
import org.notests.rxfeedback.bindSafe
import org.notests.sharedsequence.distinctUntilChanged
import org.notests.sharedsequence.drive
import org.notests.sharedsequence.map
import java.util.concurrent.TimeUnit

/**
 * Created by Ali on 5/29/2019.
 */
class LoginFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onResume() {
        super.onResume()
        listener?.onFragmentResumed(TAG_LOGIN_FRAGMENT)
        val disposable = listener?.onAttachFeedbacks(
                bindUI(),
                login()
        )
        if (disposable != null) {
            disposables.add(disposable)
        }
    }


    fun bindUI(): Feedback {
        return bindSafe<State, Event> {
            Bindings.safe(
                    subscriptions = listOf(
                            it.map { it.loginButtonEnabled }.distinctUntilChanged().drive { btn_login.isEnabled = it },
                            it.map { it.loginErrorVisibiltiy }.distinctUntilChanged().drive { txt_login_error.visibility = it },
                            it.map { it.progressBarVisibility }.distinctUntilChanged().drive { progress_login.visibility = it }
                    ),
                    events = listOf(
                            edt_id.watchChanges<Event> { Event.EnteredId(it) },
                            btn_login.watchClicks<Event> { Event.ClickedLogin }
                    )
            )
        }
    }

    fun login(): Feedback {
        return reactSafely<State, String, Event>(
                query = {
                    it.login()
                },
                effects = { id ->
                    //simulate a service call
                    Observable.timer(3, TimeUnit.SECONDS)
                            .map<Event> {
                                val doc = Doctor.finDoctorById(id)
                                if (doc != null) {
                                    Event.GotoAuthenticationResult(Result.Success(doc))
                                } else {
                                    Event.GotoAuthenticationResult(Result.Failure(RequestError.authenticationError()))
                                }
                            }
                            .asSignalLogFailure()
                }
        )
    }

}