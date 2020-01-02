package com.sukinsan.responsibility

import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.entities.fromJson
import com.sukinsan.responsibility.enums.RemindRuleEnum
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        val date = Date(0)
        val task = TaskEntity("task id", RemindRuleEnum.DAILY, "Drink water", date, null, null)

        assertEquals("task id", task.id)
        assertEquals("Drink water", task.description)
        assertEquals(RemindRuleEnum.DAILY, task.remindRule)
        assertEquals(date, task.createdAt)
        assertEquals(null, task.doneAt)
        assertEquals(null, task.failedAt)

        assertEquals(
            "TaskEntity(id='task id', remindRule=DAILY, description='Drink water', createdAt=Thu Jan 01 02:00:00 EET 1970, doneAt=null, failedAt=null)",
            task.toString()
        )
        assertEquals(
            "{\"id\":\"task id\",\"remindRule\":\"DAILY\",\"description\":\"Drink water\",\"createdAt\":\"Jan 1, 1970 2:00:00 AM\"}",
            task.toJson()
        )
        assertEquals(
            task,
            fromJson("{\"id\":\"task id\",\"remindRule\":\"DAILY\",\"description\":\"Drink water\",\"createdAt\":\"Jan 1, 1970 2:00:00 AM\"}")
        )

        val calendar = newTU()
        calendar.calendar.set(3000, 10, 10, 13, 25, 0)
        assertEquals(calendar.getCurrentHour(), 13)

        calendar.calendar.set(3000, 10, 10, 23, 25, 0)
        assertEquals(calendar.getCurrentHour(), 23)
    }
}
