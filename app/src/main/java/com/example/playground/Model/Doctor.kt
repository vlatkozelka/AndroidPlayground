package com.example.playground.Model

/**
 * Created by Ali on 5/29/2019.
 */
data class Doctor(
    val name: String,
    val id: String,
    val patients: List<Patient> = listOf()
) {


    companion object {
        fun getDoctors(): List<Doctor> {

            return arrayListOf(
                Doctor(
                    "Ali",
                    "1"
                ),
                Doctor(
                    "Walaa",
                    "2"
                ),
                Doctor(
                    "Imad",
                    "3"
                )
            )
        }

        fun finDoctorById(id: String): Doctor? {
            return getDoctors().firstOrNull { it.id == id }
        }

    }

}