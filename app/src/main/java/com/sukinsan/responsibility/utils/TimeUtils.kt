package com.sukinsan.responsibility.utils

import java.text.SimpleDateFormat
import java.util.*

fun newTU(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): TimeUtils {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day, hour, minute, second)
    return TimeUtilsImpl(calendar)
}

fun newTU(): TimeUtils {
    return TimeUtilsImpl(Calendar.getInstance())
}

interface TimeUtils {

    fun getCurrentHour(): Int

    fun friendlyDateTime(): String

    fun getDate(): Date
}

class TimeUtilsImpl(val calendar: Calendar) : TimeUtils {

    val PATTERN_TIME = "MMM d, HH:mm"
    val PATTERN_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"

    val dfISO8601: SimpleDateFormat
    val dfTime: SimpleDateFormat

    init {
        dfISO8601 = SimpleDateFormat(PATTERN_ISO8601, Locale.getDefault())
        dfTime = SimpleDateFormat(PATTERN_TIME, Locale.getDefault())
    }

    override fun getCurrentHour(): Int {
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    override fun friendlyDateTime(): String {
        return dfTime.format(calendar.time)
    }

    override fun getDate(): Date {
        return calendar.time
    }

}