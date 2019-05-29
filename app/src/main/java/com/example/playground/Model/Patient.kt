package com.example.playground.Model

/**
 * Created by Ali on 5/29/2019.
 */
data class Patient(
    val name: String,
    val age: Int,
    val reports: List<Report>
)