package com.example.playground.main.patients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.R
import com.example.playground.main.BaseFragment
import com.example.playground.main.MainActivity.Companion.TAG_PATIENTS_FRAGMENT

/**
 * Created by Ali on 5/29/2019.
 */
class PatientsFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_patients, container, false)
    }

    override fun onResume() {
        super.onResume()
        listener?.onFragmentResumed(TAG_PATIENTS_FRAGMENT)
        val disposable = listener?.onAttachFeedbacks(

        )
        if (disposable != null) {
            disposables.add(disposable)
        }
    }
}