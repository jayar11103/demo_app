package com.example.m88demoapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Extension function on String to extract the year from a date string.
 * It takes a date string in the format "yyyy-MM-dd'T'HH:mm:ss'Z'" and returns the year.
 *
 * @return A string representing the extracted year.
 */
fun String.extractYearFromDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date = inputFormat.parse(this)

    val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    return outputFormat.format(date)
}

/**
 * Extension function on Long to format the timestamp to a date string.
 * It takes a timestamp in milliseconds and returns a date string in the format "yyyy-MM-dd HH:mm:ss".
 *
 * @return A string representing the formatted date.
 */
fun Long.extractDate(): String {
    val date = Date(this)

    val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return outputFormat.format(date)
}
