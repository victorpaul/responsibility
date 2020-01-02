package com.sukinsan.responsibility

import java.util.*

fun newTU(): TimeUtils {
    return TimeUtils(Calendar.getInstance())
}

class TimeUtils(val calendar: Calendar) {

    fun getCurrentHour(): Int {
        return calendar.get(Calendar.HOUR_OF_DAY)
    }
}