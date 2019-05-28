package com.example.playground.utils

import java.util.*


/**
 * @return a UUID from this String
 */
fun String.toUUID(): UUID {
    return UUID.fromString(this)
}

/**
 * Finds a float value inside this string. The value must contain "."
 *
 * @return the first found float or -1 if not found
 */
fun String.goTempFirmwareAsFloat(): Float {
    return try {
        val floatRegex = Regex("""\d*\.\d+""")
        val matches = floatRegex.findAll(this)
        val res = matches.iterator().next().value.toFloat()
        res
    } catch (ex: Exception) {
        //we return -1 to avoid NPE for really old devices
        //these devices will get updated by force since -1 < 0.56
        -1f
    }
}

fun String.toBool(): Boolean? {
    return when (this.toLowerCase()) {
        "true" -> true
        "false" -> false
        "yes" -> true
        "no" -> false
        "0" -> false
        "1" -> true
        else -> null
    }
}
