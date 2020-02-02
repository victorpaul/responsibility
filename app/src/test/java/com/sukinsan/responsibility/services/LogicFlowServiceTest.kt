package com.sukinsan.responsibility.services

import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.entities.createEveryHourWeekly
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.utils.newTaskUtils
import com.sukinsan.responsibility.utils.newTU
import org.junit.Assert.assertEquals
import org.junit.Test

class LogicFlowServiceTest {

    @Test
    fun success_allow_task_for_every_hour_every_day_of_every_month() {
        val weeklyTask = createEveryHourWeekly("id", "desc")
        val dailyTask = createEveryHourWeekly("id", "desc")
        for (month in 0..11) {
            for (days in 0..31) {
                for (hour in 8..23) {
                    val tu = newTU(2020, month, days, hour, 25, 0)
                    assertEquals(true, newTaskUtils().isItOkToRemindNow(weeklyTask, tu).success)
                    assertEquals(true, newTaskUtils().isItOkToRemindNow(dailyTask, tu).success)
                }
            }
        }
    }

    @Test
    fun success_allows_weekly_task() {
        val task = TaskEntity(
            "id",
            RemindRuleEnum.WEEKLY_DAYS,
            listOf(9),
            listOf(7),
            emptyList(),
            listOf(1),
            null,
            "desk", null, mutableListOf(), mutableListOf()
        )
        newTU(2020, 0, 26, 9, 25, 0).apply {
            assertEquals("2020 Jan 26, 09:25", this.friendlyDateTimeYear())
            assertEquals(true, newTaskUtils().isItOkToRemindNow(task, this).success)
        }

        assertEquals(
            "Day of week rules are not met for date 2020 Jan 27, 09:25",
            newTaskUtils().isItOkToRemindNow(
                task,
                newTU(2020, 0, 27, 9, 25, 0)
            ).message
        )
    }

    @Test
    fun success_allows_daily_task() {
        val task = TaskEntity(
            "id",
            RemindRuleEnum.MONTHLY_DAYS,
            listOf(9),
            emptyList(),
            listOf(26),
            listOf(1),
            null,
            "desk", null, mutableListOf(), mutableListOf()
        )
        newTU(2020, 0, 26, 9, 25, 0).apply {
            assertEquals("2020 Jan 26, 09:25", this.friendlyDateTimeYear())
            assertEquals(true, newTaskUtils().isItOkToRemindNow(task, this).success)
        }

        assertEquals(
            "Day of month rules are not met for date 2020 Jan 27, 09:25",
            newTaskUtils().isItOkToRemindNow(
                task,
                newTU(2020, 0, 27, 9, 25, 0)
            ).message
        )
    }

    @Test
    fun fail_to_allow_to_remind_at_invalid_month() {
        val task = TaskEntity(
            "id",
            RemindRuleEnum.MONTHLY_DAYS,
            listOf(9),
            emptyList(),
            listOf(26),
            listOf(1),
            null,
            "desk", null, mutableListOf(), mutableListOf()
        )

        assertEquals(
            "Monthly rules are not met for date 2020 Feb 2, 09:25",
            newTaskUtils().isItOkToRemindNow(
                task,
                newTU(2020, 1, 2, 9, 25, 0)
            ).message
        )
    }

    @Test
    fun fail_to_allow_to_remind_at_invalid_hour() {
        val task = TaskEntity(
            "id",
            RemindRuleEnum.MONTHLY_DAYS,
            listOf(9),
            emptyList(),
            listOf(26),
            listOf(1),
            null,
            "desk", null, mutableListOf(), mutableListOf()
        )

        assertEquals(
            "Hourly rules are not met for date 2020 Jan 26, 10:25",
            newTaskUtils().isItOkToRemindNow(
                task,
                newTU(2020, 0, 26, 10, 25, 0)
            ).message
        )
    }


}