package com.sukinsan.responsibility.providers

import android.content.Context
import android.content.SharedPreferences
import com.sukinsan.responsibility.entities.createEveryHourWeekly
import com.sukinsan.responsibility.utils.DBUtilsSharedPrefImpls
import com.sukinsan.responsibility.utils.TimeUtils
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
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
        val dbProvider = DBProviderSharedPrefImpl(ctx)

        val task = createEveryHourWeekly("task id5", "Age is just a number")
        val json = dbProvider.gson.toJson(
            DBUtilsSharedPrefImpls().apply {
                save(task)
            })
        doReturn(json).`when`(sharedPrefTasks).getString(ArgumentMatchers.anyString(), ArgumentMatchers.any())

        val db = dbProvider.read()

        verify(sharedPrefTasks).getString("StorageEntity", null)
        assertEquals(1, db.getTasksAll().size)
        assertEquals(task, db.getTasksAll().get("task id5"))
    }

    @Test
    fun success_read_not_existing_db() {
        doReturn(null).`when`(sharedPrefTasks)
            .getString(ArgumentMatchers.anyString(), ArgumentMatchers.any())

        val dbProvider = DBProviderSharedPrefImpl(ctx)
        val db = dbProvider.read()

        verify(sharedPrefTasks).getString("StorageEntity", null)
        assertEquals(DBUtilsSharedPrefImpls(), db)
    }

    @Test
    fun success_write_to_db() {
        val dbProvider = spy(DBProviderSharedPrefImpl(ctx))
        val editor = mock(SharedPreferences.Editor::class.java)
        doReturn(DBUtilsSharedPrefImpls()).`when`(dbProvider).read()
        doReturn(editor).`when`(sharedPrefTasks).edit()
        doReturn(editor).`when`(editor).putString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
        doReturn(true).`when`(editor).commit()


        val task = createEveryHourWeekly("task id5", "Age is just a number")

        assertEquals(true, dbProvider.write {
            it.save(task)
            return@write true
        })

        verify(dbProvider).read()
        verify(sharedPrefTasks).getString("StorageEntity", null)

        //verifyNoMoreInteractions(editor)
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