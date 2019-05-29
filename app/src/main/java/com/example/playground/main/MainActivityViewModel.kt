package com.example.playground.main

import com.example.playground.R
import com.example.playground.main.MainActivity.Companion.TAG_ADD_PATIENT_FRAGMENT
import com.example.playground.main.MainActivity.Companion.TAG_LOGIN_FRAGMENT
import com.example.playground.main.MainActivity.Companion.TAG_PATIENTS_FRAGMENT
import com.example.playground.main.MainActivity.Companion.TAG_PATIENT_PROFILE_FRAGMENT
import com.example.playground.main.MainActivity.Companion.TAG_SPLASH_FRAGMENT
import com.zippyyum.subtemp.rxfeedback.getString

/**
 * Created by Ali on 5/29/2019.
 */

val State.actionBarTitle: String
    get() {
        return when (currentFragmentTag) {
            TAG_LOGIN_FRAGMENT -> getString(R.string.login)
            TAG_PATIENTS_FRAGMENT -> getString(R.string.patients)
            TAG_ADD_PATIENT_FRAGMENT -> getString(R.string.add_patient)
            TAG_PATIENT_PROFILE_FRAGMENT -> getString(R.string.patient_profile)
            else -> getString(R.string.app_name)
        }
    }

val State.isActionBarVisible: Boolean
    get() {
        //return currentFragmentTag != TAG_SPLASH_FRAGMENT
        //it looks stuttery when it hides then shows
        return true
    }