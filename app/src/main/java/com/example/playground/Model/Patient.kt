package com.example.playground.Model

import kotlin.random.Random

/**
 * Created by Ali on 5/29/2019.
 */
data class Patient(
        val name: String,
        val age: Int,
        val reports: List<Report>
) {


    companion object {

        fun getPatients(): List<Patient> {
            val random = Random(0)

            return listOf(
                    Patient(
                            "Patient 0",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 1",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 2",
                            random.nextInt(99) + 1,
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 3",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 4",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 5",
                            random.nextInt(99) + 1,
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 6",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 7",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 8",
                            random.nextInt(99) + 1,
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 9",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 10",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 11",
                            random.nextInt(99) + 1,
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 12",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 13",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 14",
                            random.nextInt(99) + 1,
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 15",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 16",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 17",
                            random.nextInt(99) + 1,
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 18",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 19",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 20",
                            random.nextInt(99) + 1,
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 21",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 22",
                            random.nextInt(99) + 1,
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 23",
                            random.nextInt(99) + 1,
                            Report.getReports()

                    )
            )
        }

    }

}