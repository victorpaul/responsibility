package com.sukinsan.responsibility.utils

import android.content.Context
import android.content.SharedPreferences
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.providers.newSharedPrefDB
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

//    @Test
//    fun success_save_last_message_for_task() {
//        val task = TaskEntity(
//            "task id", RemindRuleEnum.DAILY, "Drink water",
//            newTU(3000, 10, 10, 21, 10, 0).getDate()
//        )
//        val storage = newSharedPrefDB(ctx, tu)
//        val editor = mock(SharedPreferences.Editor::class.java)
//        doReturn(editor).`when`(sharedPrefTemps).edit()
//        doReturn(true).`when`(editor).commit()
//        doReturn("jan 4").`when`(tu).friendlyDate()
//
//        assertEquals(true, storage.saveLastMessage(task,"drink water"))
//
//        verify(sharedPrefTemps).edit()
//        verify(tu).friendlyDate()
//        verify(editor).putString("1-jan 4", "drink water")
//        verify(editor).commit()
//    }

//    @Test
//    fun success_get_last_message_for_task() {
//        val task = TaskEntity(
//            "task id", RemindRuleEnum.HOURLY, "Drink water",
//            newTU(3000, 10, 10, 21, 10, 0).getDate()
//        )
//        val storage = newStorageUtils(ctx, tu)
//        doReturn(null).`when`(sharedPrefTemps).getString(anyString(), any())
//        doReturn("jan 4").`when`(tu).friendlyDate()
//        doReturn("Do sport").`when`(sharedPrefTasks).getString(anyString(), any())
//
//        assertEquals("Do sport", storage.getLastMessage(task))
//        verify(tu).friendlyDate()
//        verify(sharedPrefTasks).getString("0-jan 4", null)
//    }

}