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
    fun success_get_day_of_month() {
        assertEquals(
            10,
            newTU(3000, 10, 10, 23, 25, 0).getCurrentMonthDay()
        )
        assertEquals(
            10,
            newTU(3000, 8, 10, 23, 25, 0).getCurrentMonthDay()
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

    @Test
    fun success_get_week_day() {

        newTU(2020, 0, 19, 11, 25, 0).apply {
            assertEquals("Jan 19", friendlyDate())
            assertEquals(7, getCurrentWeekDay())
        }
        newTU(2020, 0, 20, 11, 25, 0).apply {
            assertEquals("Jan 20", friendlyDate())
            assertEquals(1, getCurrentWeekDay())
        }
        newTU(2020, 0, 21, 11, 25, 0).apply {
            assertEquals("Jan 21", friendlyDate())
            assertEquals(2, getCurrentWeekDay())
        }
        newTU(2020, 0, 22, 11, 25, 0).apply {
            assertEquals("Jan 22", friendlyDate())
            assertEquals(3, getCurrentWeekDay())
        }
        newTU(2020, 0, 23, 11, 25, 0).apply {
            assertEquals("Jan 23", friendlyDate())
            assertEquals(4, getCurrentWeekDay())
        }
        newTU(2020, 0, 24, 11, 25, 0).apply {
            assertEquals("Jan 24", friendlyDate())
            assertEquals(5, getCurrentWeekDay())
        }
        newTU(2020, 0, 25, 11, 25, 0).apply {
            assertEquals("Jan 25", friendlyDate())
            assertEquals(6, getCurrentWeekDay())
        }
        newTU(2020, 0, 26, 11, 25, 0).apply {
            assertEquals("Jan 26", friendlyDate())
            assertEquals(7, getCurrentWeekDay())
        }
        newTU(2020, 0, 27, 11, 25, 0).apply {
            assertEquals("Jan 27", friendlyDate())
            assertEquals(1, getCurrentWeekDay())
        }
    }

    @Test
    fun success_get_month_day() {
        newTU(2020, 0, 27, 11, 25, 0).apply {
            assertEquals("Jan 27", friendlyDate())
            assertEquals(1, getCurrentMonth())
        }
        newTU(2020, 1, 27, 11, 25, 0).apply {
            assertEquals("Feb 27", friendlyDate())
            assertEquals(2, getCurrentMonth())
        }
        newTU(2020, 2, 27, 11, 25, 0).apply {
            assertEquals("Mar 27", friendlyDate())
            assertEquals(3, getCurrentMonth())
        }
        newTU(2020, 3, 27, 11, 25, 0).apply {
            assertEquals("Apr 27", friendlyDate())
            assertEquals(4, getCurrentMonth())
        }
        newTU(2020, 4, 27, 11, 25, 0).apply {
            assertEquals("May 27", friendlyDate())
            assertEquals(5, getCurrentMonth())
        }
        newTU(2020, 5, 27, 11, 25, 0).apply {
            assertEquals("Jun 27", friendlyDate())
            assertEquals(6, getCurrentMonth())
        }
        newTU(2020, 6, 27, 11, 25, 0).apply {
            assertEquals("Jul 27", friendlyDate())
            assertEquals(7, getCurrentMonth())
        }
        newTU(2020, 7, 27, 11, 25, 0).apply {
            assertEquals("Aug 27", friendlyDate())
            assertEquals(8, getCurrentMonth())
        }
        newTU(2020, 8, 27, 11, 25, 0).apply {
            assertEquals("Sep 27", friendlyDate())
            assertEquals(9, getCurrentMonth())
        }
        newTU(2020, 9, 27, 11, 25, 0).apply {
            assertEquals("Oct 27", friendlyDate())
            assertEquals(10, getCurrentMonth())
        }
        newTU(2020, 10, 27, 11, 25, 0).apply {
            assertEquals("Nov 27", friendlyDate())
            assertEquals(11, getCurrentMonth())
        }
        newTU(2020, 11, 27, 11, 25, 0).apply {
            assertEquals("Dec 27", friendlyDate())
            assertEquals(12, getCurrentMonth())
        }
    }
}