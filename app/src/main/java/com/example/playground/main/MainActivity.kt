package com.example.playground.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.playground.R
import com.example.playground.main.addpatient.AddPatientFragment
import com.example.playground.main.login.LoginFragment
import com.example.playground.main.patientprofile.PatientProfileFragment
import com.example.playground.main.patients.PatientsFragment
import com.example.playground.main.splash.SplashFragment
import com.example.playground.utils.exhaustive
import com.example.playground.utils.rx.attachFeedbacks
import com.example.playground.utils.rx.collectNotNull
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import org.notests.rxfeedback.Bindings
import org.notests.rxfeedback.Optional
import org.notests.rxfeedback.bindSafe
import org.notests.rxfeedback.system
import org.notests.sharedsequence.*


typealias Feedback = (Driver<State>) -> Signal<Event>

class MainActivity : AppCompatActivity(), FragmentListener {

    companion object {
        const val TAG_SPLASH_FRAGMENT = "splash"
        const val TAG_LOGIN_FRAGMENT = "login_fragment"
        const val TAG_PATIENTS_FRAGMENT = "patients_fragment"
        const val TAG_ADD_PATIENT_FRAGMENT = "add_patients_fragment"
        const val TAG_PATIENT_PROFILE_FRAGMENT = "patient_profile_fragment"
    }

    val disposables = arrayListOf<Disposable>()
    val eventsSubject = PublishSubject.create<Event>()
    val eventsSignal = Signal(eventsSubject)
    var stateDriver: Driver<State>? = null
    var savedState: State? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(TAG_SPLASH_FRAGMENT, SplashFragment())
    }


    override fun onResume() {
        super.onResume()

        stateDriver = Driver.system(
            savedState?.copy(route = Optional.None()) ?: State(),
            MainStateReducer::reduce,
            bindUI()
        )
        stateDriver?.let { disposables.add(it.drive()) }
        actionBar?.title = "test"
    }

    override fun onPause() {
        super.onPause()
        disposables.forEach { it.dispose() }
    }


    fun bindUI(): Feedback {
        return bindSafe<State, Event> { stateDriver ->
            Bindings.safe(
                subscriptions = listOf(
                    stateDriver.map { it.isActionBarVisible }.distinctUntilChanged().drive { showActionBar(it) },
                    stateDriver.map { it.actionBarTitle }.distinctUntilChanged().drive { supportActionBar?.title = it },
                    stateDriver.map { it.route }.collectNotNull().distinctUntilChanged().drive { gotoRoute(it) },
                    stateDriver.drive { savedState = it }
                ),
                events = listOf(
                    eventsSignal
                )
            )
        }
    }


    private fun showActionBar(show: Boolean) {
        if (show) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }


    override fun onFragmentResumed(tag: String) {
        eventsSubject.onNext(Event.NavigatedToFragment(tag))
    }

    override fun onAttachFeedbacks(vararg feedbacks: (Driver<State>) -> Signal<Event>): Disposable? {
        return stateDriver?.let { driver ->
            attachFeedbacks(driver, eventsSubject, *feedbacks)
        }
    }


    fun gotoRoute(route: State.Route) {
        when (route) {
            State.Route.Login -> {
                replaceFragment(TAG_LOGIN_FRAGMENT, LoginFragment())
            }
            State.Route.Patients -> {
                replaceFragment(TAG_PATIENTS_FRAGMENT, PatientsFragment())
            }
            State.Route.AddPatient -> {
                replaceFragment(TAG_ADD_PATIENT_FRAGMENT, AddPatientFragment())
            }
            State.Route.PatientProfile -> {
                replaceFragment(TAG_PATIENTS_FRAGMENT, PatientProfileFragment())
            }
            is State.Route.ViewReport -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(route.report))
                startActivity(browserIntent)
            }
            State.Route.Quit -> finish()
        }.exhaustive
    }

    private fun replaceFragment(tag: String, fragment: BaseFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commitAllowingStateLoss()

    }

    override fun onBackPressed() {
        eventsSubject.onNext(Event.BackPressed)
    }


}
