package com.sukinsan.responsibility.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class TimeUtilsTest {

    @Test
    fun success_get_hour_of_the_day() {
        assertEquals(
            newTU(3000, 10, 10, 13, 25, 0).getCurrentHour(),
            13
        )
        assertEquals(
            newTU(3000, 10, 10, 23, 25, 0).getCurrentHour(),
            23
        )
    }

    @Test
    fun success_get_date_time_as_string() {
        assertEquals(
            newTU(3000, 10, 10, 23, 25, 0).friendlyDateTime(),
            "Nov 10, 23:25"
        )
    }

    @Test
    fun success_get_time_as_string() {
        assertEquals(
            newTU(3000, 10, 10, 23, 25, 0).friendlyTime(),
            "23:25"
        )
    }

    @Test
    fun success_get_date_as_string() {
        assertEquals(
            newTU(3000, 10, 10, 23, 25, 0).friendlyDate(),
            "Nov 10"
        )
    }

    @Test
    fun success_get_date() {
        assertEquals(
            newTU(3000, 10, 10, 23, 25, 0).getDate().toString(),
            Date(32530800300944L).toString()
        )
    }
}