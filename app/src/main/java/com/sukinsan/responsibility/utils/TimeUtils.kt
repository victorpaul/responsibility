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

    fun getCurrentMonthDay(): Int

    fun getCurrentWeekDay(): Int

    fun getCurrentMonth(): Int

    fun friendlyDateTime(): String

    fun friendlyDateTimeYear(): String

    fun friendlyDate(): String

    fun friendlyTime(): String

    fun getDate(): Date
}

class TimeUtilsImpl(val calendar: Calendar) : TimeUtils {

    val PATTERN_FRIENDLY_TIME = "HH:mm"
    val PATTERN_FRIENDLY_DATE = "MMM d"
    val PATTERN_FRIENDLY_DATE_TIME = "MMM d, HH:mm"
    val PATTERN_FRIENDLY_DATE_TIME_YEAR = "yyyy MMM d, HH:mm"
    val PATTERN_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"

    val dfISO8601: SimpleDateFormat by lazy { SimpleDateFormat(PATTERN_ISO8601, Locale.getDefault()) }
    val dfDateTime: SimpleDateFormat by lazy { SimpleDateFormat(PATTERN_FRIENDLY_DATE_TIME, Locale.getDefault()) }
    val dfDateTimeYear: SimpleDateFormat by lazy { SimpleDateFormat(PATTERN_FRIENDLY_DATE_TIME_YEAR, Locale.getDefault()) }
    val dfDate: SimpleDateFormat by lazy { SimpleDateFormat(PATTERN_FRIENDLY_DATE, Locale.getDefault()) }
    val dfTime: SimpleDateFormat by lazy { SimpleDateFormat(PATTERN_FRIENDLY_TIME, Locale.getDefault()) }

    override fun getCurrentHour(): Int {
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    override fun getCurrentMonthDay(): Int {
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    override fun getCurrentWeekDay(): Int {
        val dow = calendar.get(Calendar.DAY_OF_WEEK)
        if (dow == 1) {
            return 7 // monday is 1, sunday is 7
        }
        return dow - 1
    }

    override fun getCurrentMonth(): Int {
        return calendar.get(Calendar.MONTH) + 1 // jan is 1, dec is 12
    }

    override fun friendlyDateTime(): String {
        return dfDateTime.format(calendar.time)
    }

    override fun friendlyDateTimeYear(): String {
        return dfDateTimeYear.format(calendar.time)
    }

    override fun friendlyDate(): String {
        return dfDate.format(calendar.time)
    }

    override fun friendlyTime(): String {
        return dfTime.format(calendar.time)
    }

    override fun getDate(): Date {
        return calendar.time
    }

}