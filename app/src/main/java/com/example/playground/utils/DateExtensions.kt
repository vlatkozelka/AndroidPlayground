package com.example.playground.utils

import java.text.DateFormat
import java.util.*


/**
 * Add field date to current date
 */
fun Date.add(field: Int, amount: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(field, amount)

    this.time = cal.time.time

    cal.clear()

    return this
}

fun Date.addYears(years: Int): Date {
    return add(Calendar.YEAR, years)
}

fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}

fun Date.addDays(days: Int): Date {
    return add(Calendar.DAY_OF_MONTH, days)
}

fun Date.addHours(hours: Int): Date {
    return add(Calendar.HOUR_OF_DAY, hours)
}

fun Date.addMinutes(minutes: Int): Date {
    return add(Calendar.MINUTE, minutes)
}

fun Date.addSeconds(seconds: Int): Date {
    return add(Calendar.SECOND, seconds)
}

fun Date.addMillis(millis: Int): Date {
    return add(Calendar.MILLISECOND, millis)
}

fun Int.secondsAgo(): Date {
    return Date().addSeconds(-this)
}

fun Int.millisAgo(): Date {
    return Date().addMillis(-this)
}

fun Int.minsAgo(): Date {
    return Date().addMinutes(-this)
}

fun Int.daysAgo(): Date {
    return Date().addDays(-this)
}

fun Int.days(): Date{
    return Date().addDays(this)
}

fun Int.hoursAgo(): Date {
    return Date().addHours(-this)
}

fun Int.minutesAgo(): Date {
    return Date().addMinutes(-this)
}

fun Date.localizedDate(): String {
    val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
    return df.format(this)
}