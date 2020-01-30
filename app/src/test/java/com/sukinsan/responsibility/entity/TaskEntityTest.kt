package com.sukinsan.responsibility.entity

import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.entities.createEveryHourWeekly
import com.sukinsan.responsibility.enums.RemindRuleEnum
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Test
import java.util.*

class TaskEntityTest {

    @Test
    fun success_create_entity() {
        val task = createEveryHourWeekly("task id", "Drink water")

        Assert.assertEquals("task id", task.id)
        Assert.assertEquals("Drink water", task.description)
        Assert.assertEquals(RemindRuleEnum.WEEKLY_DAYS, task.remindRule)
        Assert.assertEquals(
            listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
            task.rulesHours
        )
        Assert.assertEquals(listOf(1, 2, 3, 4, 5, 6, 7), task.rulesWeek)
        Assert.assertEquals(0, task.rulesDays.size)

        Assert.assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), task.rulesMonths)

        Assert.assertEquals(
            "TaskEntity(id='task id', remindRule=WEEKLY_DAYS, " +
                    "rulesHours=[8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23], " +
                    "rulesWeek=[1, 2, 3, 4, 5, 6, 7], " +
                    "rulesDays=[], " +
                    "rulesMonths=[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12], " +
                    "rulesExactDate=null, " +
                    "description='Drink water', " +
                    "workerManagerId=null, " +
                    "notifiedAt=[], " +
                    "tags=[])",
            task.toString()
        )

        Assert.assertEquals(
            TaskEntity(
                "task id",
                RemindRuleEnum.WEEKLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                listOf(1, 2, 3, 4, 5, 6, 7),
                emptyList(),
                listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                null, "Drink water", null, mutableListOf(), mutableListOf()
            ), task
        )
    }

    @Test
    fun success_get_uuid() {
        val task = createEveryHourWeekly("task id", "Drink water")
        task.workerManagerId = "4f3f1826-6b7e-4fd2-a4c2-b488fea647fd"
        assertEquals(
            UUID.fromString("4f3f1826-6b7e-4fd2-a4c2-b488fea647fd"),
            task.getWorkerUUID()
        )
    }

    @Test
    fun fail_to_get_uuid() {
        val task = createEveryHourWeekly("task id", "Drink water")

        assertEquals(
            null,
            task.getWorkerUUID()
        )
    }

    @Test
    fun success_describe_hours() {

        assertEquals(
            "8-23",
            TaskEntity(
                "", RemindRuleEnum.MONTHLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                emptyList(), emptyList(), emptyList(), null,
                "", null, mutableListOf(), mutableListOf()
            ).describeRulesHors(false)
        )
    }

    @Test
    fun success_describe_week() {

        assertEquals(
            "mon-sun",
            TaskEntity(
                "", RemindRuleEnum.MONTHLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                emptyList(), emptyList(), emptyList(), null,
                "", null, mutableListOf(), mutableListOf()
            ).describeRulesWeekle(false)
        )
    }

    @Test
    fun success_describe_days() {

        assertEquals(
            "1-31",
            TaskEntity(
                "", RemindRuleEnum.MONTHLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                emptyList(), emptyList(), emptyList(), null,
                "", null, mutableListOf(), mutableListOf()
            ).describeRulesDaily(false)
        )
    }

    @Test
    fun success_describe_months() {

        assertEquals(
            "1-12",
            TaskEntity(
                "", RemindRuleEnum.MONTHLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                emptyList(), emptyList(), emptyList(), null,
                "", null, mutableListOf(), mutableListOf()
            ).describeRulesMonthly(false)
        )
    }

    @Test
    fun success_describe_all_rules() {
        assertEquals(
            """
            Hours: 8h,9h,10h,11h,12h,13h,14h,15h,16h,17h,18h,19h,20h,21h,22h,23h
            Week days: Mon,Tue,Wed,Thu,Fri,Sat,Sun
            Month days: 1d,2d,7d,9d,12d,1d,15d,16d,17d,18d,19d,20d,21d,22d,23d,24d,25d
            Months: Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
            """.trimIndent(),
            TaskEntity(
                "", RemindRuleEnum.MONTHLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                listOf(1, 2, 3, 4, 5, 6, 7),
                listOf(1, 2, 7, 9, 12, 1, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25),
                listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), null,
                "", null, mutableListOf(), mutableListOf()
            ).describeAllRules(true)
        )
    }

    @Test
    fun success_describe_hours_friendly() {

        assertEquals(
            "All day long",
            TaskEntity(
                "", RemindRuleEnum.MONTHLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                emptyList(), emptyList(), emptyList(), null,
                "", null, mutableListOf(), mutableListOf()
            ).describeRulesHors(true)
        )
    }

    @Test
    fun success_describe_weekdays_friendly() {
        assertEquals(
            "every day",
            TaskEntity(
                "", RemindRuleEnum.MONTHLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                emptyList(), emptyList(), emptyList(), null,
                "", null, mutableListOf(), mutableListOf()
            ).describeRulesWeekle(true)
        )
    }

    @Test
    fun success_describe_monthdays_friendly() {
        assertEquals(
            "every day",
            TaskEntity(
                "", RemindRuleEnum.MONTHLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                emptyList(), emptyList(), emptyList(), null,
                "", null, mutableListOf(), mutableListOf()
            ).describeRulesDaily(true)
        )
    }

    @Test
    fun success_describe_months_friendly() {
        assertEquals(
            "every month",
            TaskEntity(
                "", RemindRuleEnum.MONTHLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                emptyList(), emptyList(), emptyList(), null,
                "", null, mutableListOf(), mutableListOf()
            ).describeRulesMonthly(true)
        )
    }

    @Test
    fun success_describe_all_rules_friendly() {
        assertEquals(
            "every month",
            TaskEntity(
                "", RemindRuleEnum.MONTHLY_DAYS,
                listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
                emptyList(), emptyList(), emptyList(), null,
                "", null, mutableListOf(), mutableListOf()
            ).describeAllRules(true)
        )
    }

}