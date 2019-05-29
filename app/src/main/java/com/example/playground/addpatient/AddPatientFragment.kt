package com.example.playground.addpatient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.Model.Patient
import com.example.playground.R
import com.example.playground.main.BaseFragment
import com.example.playground.main.Event
import com.example.playground.main.Feedback
import com.example.playground.main.MainActivity.Companion.TAG_ADD_PATIENT_FRAGMENT
import com.example.playground.main.State
import com.example.playground.utils.Result
import com.example.playground.utils.rx.asSignalLogFailure
import com.example.playground.utils.rx.reactSafely
import com.zippyyum.subtemp.utilities.watchChanges
import com.zippyyum.subtemp.utilities.watchClicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_add_patient.*
import org.notests.rxfeedback.Bindings
import org.notests.rxfeedback.bindSafe
import org.notests.sharedsequence.distinctUntilChanged
import org.notests.sharedsequence.drive
import org.notests.sharedsequence.map
import java.util.concurrent.TimeUnit

/**
 * Created by Ali on 5/29/2019.
 */
class AddPatientFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_patient, container, false)
    }


    override fun onResume() {
        super.onResume()
        listener?.onFragmentResumed(TAG_ADD_PATIENT_FRAGMENT)
        val disposable = listener?.onAttachFeedbacks(
                bindUI(),
                addPatient()
        )
        if (disposable != null) {
            disposables.add(disposable)
        }
    }


    fun bindUI(): Feedback {
        return bindSafe<State, Event> { state ->
            Bindings.safe(
                    subscriptions = listOf(
                            state.map { it.addButtonEnabled }.distinctUntilChanged().drive { btn_add.isEnabled = it },
                            state.map { it.addPatientProgressVisibility }.distinctUntilChanged().drive { progress_add_patient.visibility = it }
                    ),
                    events = listOf(
                            btn_add.watchClicks<Event> { Event.ClickedSubmitAddPatient },
                            edt_name.watchChanges<Event> { Event.PatientNameChanged(it) },
                            edt_device_number.watchChanges<Event> { Event.PatientDeviceSerialChanged(it) },
                            edt_phone_number.watchChanges<Event> { Event.PatientNumberChanged(it) }
                    )
            )
        }
    }


    fun addPatient(): Feedback {
        return reactSafely<State, Patient, Event>(
                query = {
                    it.addPatient()
                },
                effects = { patient ->
                    //simulate api call
                    Observable.timer(2, TimeUnit.SECONDS)
                            .map<Event> {
                                //throw a success every time
                                //we already demoed how to show errors
                                Event.GotAddPatientResult(Result.Success(patient))
                            }.asSignalLogFailure()
                }
        )
    }

}