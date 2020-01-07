package com.sukinsan.responsibility.entity

import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.entities.taskFromJson
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.utils.newTU
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Test
import java.util.*

class TaskEntityTest {

    @Test
    fun success_create_entity() {
        val date = Date(0)
        val task = TaskEntity("task id", RemindRuleEnum.DAILY, "Drink water", date)

        Assert.assertEquals("task id", task.id)
        Assert.assertEquals("Drink water", task.description)
        Assert.assertEquals(RemindRuleEnum.DAILY, task.remindRule)
        Assert.assertEquals(date, task.createdAt)
        Assert.assertEquals(null, task.doneAt)
        Assert.assertEquals(null, task.failedAt)

        Assert.assertEquals(
            "TaskEntity(id='task id', remindRule=DAILY, description='Drink water', createdAt=Thu Jan 01 02:00:00 EET 1970, doneAt=null, failedAt=null, workerManagerId=null)",
            task.toString()
        )

        Assert.assertEquals(
            TaskEntity("task id",  RemindRuleEnum.DAILY, "Drink water", date, null, null),
            task
        )
    }

    @Test
    fun success_json_serialization_deserialization() {
        val task = TaskEntity(
            "task id",  RemindRuleEnum.DAILY, "Drink water",
            newTU(3000, 10, 10, 21, 10, 0).getDate(),
            newTU(3000, 10, 10, 22, 25, 0).getDate(),
            newTU(3000, 10, 10, 23, 30, 0).getDate(), "UUID-UUID"
        )

        Assert.assertEquals(
            "{\"id\":\"task id\",\"remindRule\":\"DAILY\",\"description\":\"Drink water\",\"createdAt\":\"Nov 10, 3000 9:10:00 PM\",\"doneAt\":\"Nov 10, 3000 10:25:00 PM\",\"failedAt\":\"Nov 10, 3000 11:30:00 PM\",\"workerManagerId\":\"UUID-UUID\"}",
            task.toJson()
        )

        val expectedTask =
            taskFromJson("{\"id\":\"task id\",\"notificationId\":1,\"remindRule\":\"DAILY\",\"description\":\"Drink water\",\"createdAt\":\"Nov 10, 3000 9:10:00 PM\",\"doneAt\":\"Nov 10, 3000 10:25:00 PM\",\"failedAt\":\"Nov 10, 3000 11:30:00 PM\",\"workerManagerId\":\"UUID-UUID\"}")
        // todo, fix equals
        // Assert.assertEquals(task, expectedTask)
        Assert.assertEquals(task.toString(), expectedTask.toString())

    }

    @Test
    fun success_get_uuid() {
        val task = TaskEntity(
            "task id",  RemindRuleEnum.DAILY, "Drink water",
            newTU(3000, 10, 10, 21, 10, 0).getDate()
        )
        task.workerManagerId = "4f3f1826-6b7e-4fd2-a4c2-b488fea647fd"

        assertEquals(
            UUID.fromString("4f3f1826-6b7e-4fd2-a4c2-b488fea647fd"),
            task.getWorkerUUID()
        )
    }

    @Test
    fun fail_to_get_uuid() {
        val task = TaskEntity(
            "task id",  RemindRuleEnum.DAILY, "Drink water",
            newTU(3000, 10, 10, 21, 10, 0).getDate()
        )

        assertEquals(
            null,
            task.getWorkerUUID()
        )
    }

}