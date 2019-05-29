package com.example.playground.main.patientprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.R
import com.example.playground.main.BaseFragment
import com.example.playground.main.MainActivity.Companion.TAG_PATIENT_PROFILE_FRAGMENT

/**
 * Created by Ali on 5/29/2019.
 */

class PatientProfileFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_patient_profile, container, false)
    }


    override fun onResume() {
        super.onResume()
        listener?.onFragmentResumed(TAG_PATIENT_PROFILE_FRAGMENT)
    }

}