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
        Assert.assertEquals(listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23), task.rulesHours)
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
    fun sdfsf() {
        fail(); // todo im here
//        fun describeRulesHors(friendly: Boolean): String {
//            return ""
//        }
//
//        fun describeRulesWeekle(friendly: Boolean): String {
//            return ""
//        }
//
//        fun describeRulesDaily(friendly: Boolean): String {
//            return ""
//        }
//
//        fun describeRulesMonthly(friendly: Boolean): String {
//            return ""
//        }
    }

}