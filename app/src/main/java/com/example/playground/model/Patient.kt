package com.example.playground.model

/**
 * Created by Ali on 5/29/2019.
 */
data class Patient(
        val name: String,
        val reports: List<Report>,
        val deviceSerial: String = "123456789",
        val phoneNumber: String = "+961 76833599"
) {
    companion object {

        fun getPatients(): MutableList<Patient> {

            return mutableListOf(
                    Patient(
                            "Patient 0",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 1",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 2",
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 3",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 4",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 5",
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 6",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 7",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 8",
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 9",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 10",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 11",
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 12",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 13",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 14",
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 15",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 16",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 17",
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 18",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 19",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 20",
                            Report.getReports()

                    ),
                    Patient(
                            "Patient 21",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 22",
                            Report.getReports()
                    ),
                    Patient(
                            "Patient 23",
                            Report.getReports()

                    )
            )
        }

    }

}