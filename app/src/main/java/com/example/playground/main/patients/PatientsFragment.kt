package com.example.playground.main.patients

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.R
import com.example.playground.main.BaseFragment
import com.example.playground.main.Event
import com.example.playground.main.Feedback
import com.example.playground.main.MainActivity.Companion.TAG_PATIENTS_FRAGMENT
import com.example.playground.main.State
import com.zippyyum.subtemp.utilities.watchClicks
import kotlinx.android.synthetic.main.fragment_patients.*
import org.notests.rxfeedback.Bindings
import org.notests.rxfeedback.bindSafe
import org.notests.sharedsequence.distinctUntilChanged
import org.notests.sharedsequence.drive
import org.notests.sharedsequence.map

/**
 * Created by Ali on 5/29/2019.
 */
class PatientsFragment : BaseFragment() {

    private var adapter: PatientsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_patients, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_patients.layoutManager = LinearLayoutManager(context)
        adapter = PatientsAdapter()
        recycler_patients.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        listener?.onFragmentResumed(TAG_PATIENTS_FRAGMENT)
        val disposable = listener?.onAttachFeedbacks(
                bindUI()
        )
        if (disposable != null) {
            disposables.add(disposable)
        }
    }


    fun bindUI(): Feedback {
        return bindSafe<State, Event> { state ->
            Bindings.safe(
                    subscriptions = listOf(
                            state.map { it.patientsViewModels }.distinctUntilChanged().drive { adapter?.update(it) },
                            state.map { it.noPatientsTextVisibility }.distinctUntilChanged().drive { txt_no_patients.visibility = it }
                    ),
                    events = listOf(
                            btn_add_patient.watchClicks<Event> { Event.ClickedAddPatient }
                    )
            )
        }
    }


}