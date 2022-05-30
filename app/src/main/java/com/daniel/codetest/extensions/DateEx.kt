package com.daniel.codetest.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Extension function on Date class which returns a string
 *
 * @param formatter Date-time which need to be convert to a string in SimpleDateFormat
 * @return Formatted date string
 */
fun Date.convertToFormattedTimeString(formatter: SimpleDateFormat): String? {
    return try {
        formatter.format(this)
    } catch (e: Exception) {
        null
    }
}
