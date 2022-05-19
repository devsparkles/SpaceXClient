package com.devsparkle.spacexclient.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit


fun Date.launchDate(): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    return format.format(this)
}

fun Date.launchTime(): String {
    val format = SimpleDateFormat("HH:mm:ss", Locale.US)
    return format.format(this)
}

fun Date.IsFutureDate(): Boolean {
    try {
        val tz: TimeZone = TimeZone.getTimeZone("UTC")
        val now = Calendar.getInstance(tz)
        return this.after(now.time)
    } catch (e: ParseException) {
        e.printStackTrace()
        return false
    }
}

fun Date.daysSinceOrFromDate(): Long {
    return TimeUnit.DAYS.convert(Date().time - time, TimeUnit.MILLISECONDS)
}

fun Date.toISOUTCFormat(): String {
    val tz: TimeZone = TimeZone.getTimeZone("UTC")
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    format.timeZone = tz
    return format.format(this)
}