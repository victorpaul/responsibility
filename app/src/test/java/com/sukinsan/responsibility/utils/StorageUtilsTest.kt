package com.sukinsan.responsibility.utils

import android.content.Context
import android.content.SharedPreferences
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class StorageUtilsTest {

    val ctx = mock(Context::class.java)
    val tu = mock(TimeUtils::class.java)
    val sharedPrefTasks: SharedPreferences = mock(SharedPreferences::class.java)
    val sharedPrefTemps: SharedPreferences = mock(SharedPreferences::class.java)


    @Before
    fun prepare() {
        doReturn(sharedPrefTasks).`when`(ctx).getSharedPreferences(eq("Tasks"), anyInt())
        doReturn(sharedPrefTemps).`when`(ctx).getSharedPreferences(eq("Temps"), anyInt())
    }

    @After
    fun finish() {
        verify(ctx).getSharedPreferences("Tasks", Context.MODE_PRIVATE)
        verify(ctx).getSharedPreferences("Temps", Context.MODE_PRIVATE)

        verifyNoMoreInteractions(ctx, tu, sharedPrefTasks, sharedPrefTemps)
        reset(ctx, tu, sharedPrefTasks, sharedPrefTemps)
    }

    @Test
    fun success_save_task() {
        val task = mock(TaskEntity::class.java)
        val editor = mock(SharedPreferences.Editor::class.java)
        doReturn(editor).`when`(sharedPrefTasks).edit()
        doReturn("task id").`when`(task).id
        doReturn("task json").`when`(task).toJson()
        doReturn(true).`when`(editor).commit()

        val storage = newStorageUtils(ctx, tu)
        assertEquals(true, storage.save(task))

        verify(sharedPrefTasks).edit()
        verify(editor).putString("task id", "task json")
        verify(editor).commit()
    }

    @Test
    fun success_get_task_by_id() {
        val storage = newStorageUtils(ctx, tu)
        val task = TaskEntity(
            "task id", RemindRuleEnum.DAILY, "Drink water",
            newTU(3000, 10, 10, 21, 10, 0).getDate()
        )
        doReturn(task.toJson()).`when`(sharedPrefTasks).getString(anyString(), any())

        assertEquals(task.toString(), storage.getById(task.id).toString())
        verify(sharedPrefTasks).getString("task id", null)
    }

    @Test
    fun fail_to_get_task_by_id() {
        val storage = newStorageUtils(ctx, tu)
        doReturn(null).`when`(sharedPrefTasks).getString(anyString(), any())

        assertEquals(null, storage.getById("task id"))
        verify(sharedPrefTasks).getString("task id", null)
    }

    @Test
    fun success_save_last_message_for_task() {
        val task = TaskEntity(
            "task id", RemindRuleEnum.DAILY, "Drink water",
            newTU(3000, 10, 10, 21, 10, 0).getDate()
        )
        val storage = newStorageUtils(ctx, tu)
        val editor = mock(SharedPreferences.Editor::class.java)
        doReturn(editor).`when`(sharedPrefTemps).edit()
        doReturn(true).`when`(editor).commit()
        doReturn("jan 4").`when`(tu).friendlyDate()

        assertEquals(true, storage.saveLastMessage(task,"drink water"))

        verify(sharedPrefTemps).edit()
        verify(tu).friendlyDate()
        verify(editor).putString("1-jan 4", "drink water")
        verify(editor).commit()
    }

    @Test
    fun success_get_last_message_for_task() {
        val task = TaskEntity(
            "task id", RemindRuleEnum.HOURLY, "Drink water",
            newTU(3000, 10, 10, 21, 10, 0).getDate()
        )
        val storage = newStorageUtils(ctx, tu)
        doReturn(null).`when`(sharedPrefTemps).getString(anyString(), any())
        doReturn("jan 4").`when`(tu).friendlyDate()
        doReturn("Do sport").`when`(sharedPrefTasks).getString(anyString(), any())

        assertEquals("Do sport", storage.getLastMessage(task))
        verify(tu).friendlyDate()
        verify(sharedPrefTasks).getString("0-jan 4", null)
    }

}