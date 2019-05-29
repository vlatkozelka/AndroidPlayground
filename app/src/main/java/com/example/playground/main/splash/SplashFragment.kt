package com.example.playground.main.splash

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.R

/**
 * Created by Ali on 5/29/2019.
 */

class SplashFragment: Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_splash,container,false)
        return view
    }







}
