package com.example.playground.main.patientprofile

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.R
import com.example.playground.main.BaseFragment
import com.example.playground.main.Event
import com.example.playground.main.Feedback
import com.example.playground.main.MainActivity.Companion.TAG_PATIENT_PROFILE_FRAGMENT
import com.example.playground.main.State
import kotlinx.android.synthetic.main.fragment_patient_profile.*
import org.notests.rxfeedback.Bindings
import org.notests.rxfeedback.bindSafe
import org.notests.sharedsequence.*

/**
 * Created by Ali on 5/29/2019.
 */

class PatientProfileFragment : BaseFragment() {

    lateinit var adapter: ReportsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_patient_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_reports.layoutManager = LinearLayoutManager(context)
        adapter = ReportsAdapter()
        recycler_reports.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        listener?.onFragmentResumed(TAG_PATIENT_PROFILE_FRAGMENT)
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
                            state.map { it.patientProfileName }.distinctUntilChanged().drive { collapsingToolbar.title = it },
                            state.map { it.patientProfileName }.distinctUntilChanged().drive { toolbar.title = it },
                            state.map { it.reportViewModels }.distinctUntilChanged().drive { adapter.update(it) }
                    ),
                    events = listOf(
                            adapter.clicksSignal
                    )
            )
        }
    }


}