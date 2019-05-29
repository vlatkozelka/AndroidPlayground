package com.example.playground.main.patientprofile

import android.view.View
import com.example.playground.main.State
import org.notests.rxfeedback.Optional
import java.text.SimpleDateFormat

/**
 * Created by Ali on 5/29/2019.
 */


data class ReportViewModel(
        val type: String,
        val signature: String?,
        val date: String
) {
    val signatureVisibility: Int
        get() {
            return if (signature != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
}


val State.reportViewModels: List<ReportViewModel>
    get() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val currentPatient = currentPatient
        return when (currentPatient) {
            is Optional.Some -> {
                currentPatient.data.reports.map {
                    ReportViewModel(
                            it.type,
                            it.signature,
                            dateFormat.format(it.date)
                    )
                }
            }
            is Optional.None -> listOf()
        }
    }


val State.patientProfileName: String
    get() {
        val currentPatient = currentPatient
        return when (currentPatient) {
            is Optional.Some -> {
                currentPatient.data.name
            }
            is Optional.None -> ""
        }
    }