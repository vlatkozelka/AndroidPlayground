package com.example.playground.main

import android.content.Context
import android.support.v4.app.Fragment
import io.reactivex.disposables.Disposable
import java.lang.RuntimeException

/**
 * Created by Ali on 5/29/2019.
 */
open class BaseFragment : Fragment() {

    var listener: FragmentListener? = null
    var disposables = arrayListOf<Disposable>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is FragmentListener) {
            listener = context
        } else {
            throw RuntimeException("Activity must implement fragment listener")
        }
    }

    override fun onPause() {
        super.onPause()
        disposables.forEach { it.dispose() }
    }


}