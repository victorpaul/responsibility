package com.sukinsan.responsibility.providers

import android.content.Context
import android.content.SharedPreferences
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.utils.DBUtilsSharedPrefImpls
import com.sukinsan.responsibility.utils.TimeUtils
import com.sukinsan.responsibility.utils.newTU
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

class DBProviderSharedPrefTest {

    val ctx = mock(Context::class.java)
    val tu = mock(TimeUtils::class.java)
    val sharedPrefTasks: SharedPreferences = mock(SharedPreferences::class.java)


    @Before
    fun prepare() {
        doReturn(sharedPrefTasks).`when`(ctx)
            .getSharedPreferences(ArgumentMatchers.anyString(), anyInt())
    }

    @After
    fun finish() {
        verify(ctx).getSharedPreferences("Main", Context.MODE_PRIVATE)

        verifyNoMoreInteractions(ctx, tu, sharedPrefTasks)
        reset(ctx, tu, sharedPrefTasks)
    }

    @Test
    fun success_read_existing_db() {
        val task = TaskEntity(
            "task id5", RemindRuleEnum.DAILY, "Age is just a number",
            newTU(2020, 0, 22, 14, 4, 8).getDate(), null, null
        )
        doReturn(
            "{\"tasks\":{\"task id5\":{\"id\":\"task id5\",\"remindRule\":\"DAILY\",\"description\":\"Age is just a number\",\"createdAt\":\"Jan 22, 2020 2:04:08 PM\"}},\"keyValue\":{}}"
        ).`when`(sharedPrefTasks).getString(ArgumentMatchers.anyString(), ArgumentMatchers.any())

        val dbProvider = DBProviderSharedPrefImpl(ctx, tu)

        val db = dbProvider.read()

        verify(sharedPrefTasks).getString("StorageEntity", null)
        assertEquals(1, db.getTasksAll().size)
        assertEquals(task.toString(), db.getTasksAll().get("task id5").toString())
    }

    @Test
    fun success_read_not_existing_db() {
        doReturn(null).`when`(sharedPrefTasks)
            .getString(ArgumentMatchers.anyString(), ArgumentMatchers.any())

        val dbProvider = DBProviderSharedPrefImpl(ctx, tu)
        val db = dbProvider.read()

        verify(sharedPrefTasks).getString("StorageEntity", null)
        assertEquals(DBUtilsSharedPrefImpls(tu), db)
    }

    @Test
    fun success_serialising_json() {
        fail()
    }

    @Test
    fun success_derialising_json() {
        fail()
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