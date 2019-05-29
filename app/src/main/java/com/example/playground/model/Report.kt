package com.example.playground.model

import com.example.playground.utils.daysAgo
import java.util.*

/**
 * Created by Ali on 5/29/2019.
 */
data class Report(
        val date: Date,
        val document: String,
        val type: String
) {

    companion object {


        fun getReports(): List<Report> {
            return listOf(
                    Report(
                            5.daysAgo(),
                            "document1",
                            "Test type A"
                    ),
                    Report(
                            5.daysAgo(),
                            "document1",
                            "Test type A"
                    ),
                    Report(
                            3.daysAgo(),
                            "document1",
                            "Test type C"
                    ),
                    Report(
                            2.daysAgo(),
                            "document1",
                            "Test type ADE"
                    ),
                    Report(
                            1.daysAgo(),
                            "document1",
                            "Test type A31"
                    )
            )
        }

    }


}