package com.sukinsan.responsibility.providers

import android.content.Context
import android.content.SharedPreferences
import com.sukinsan.responsibility.entities.createEveryHourWeekly
import com.sukinsan.responsibility.utils.DBUtilsSharedPrefImpls
import com.sukinsan.responsibility.utils.TimeUtils
import org.junit.After
import org.junit.Assert.assertEquals
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
        val dbProvider = DBProviderSharedPrefImpl(ctx)

        val task = createEveryHourWeekly("task id5", "Age is just a number")
        val json = dbProvider.gson.toJson(
            DBUtilsSharedPrefImpls().apply {
                save(task)
            })
        doReturn(json).`when`(sharedPrefTasks).getString(ArgumentMatchers.anyString(), ArgumentMatchers.any())

        val db = dbProvider.read()

        verify(sharedPrefTasks).getString("StorageEntity", null)
        assertEquals(1, db.getTasksMap().size)
        assertEquals(task, db.getTasksMap().get("task id5"))
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
        val dbUtil = spy(DBUtilsSharedPrefImpls())
        doReturn(dbUtil).`when`(dbProvider).read()
        doReturn(editor).`when`(sharedPrefTasks).edit()
        doReturn(editor).`when`(editor).putString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
        doReturn(true).`when`(editor).commit()
        val task = createEveryHourWeekly("task id5", "Age is just a number")

        assertEquals(true, dbProvider.write {
            it.save(task)
            return@write true
        })

        verify(dbProvider).read()
        verify(sharedPrefTasks).edit()
        verify(editor).putString("StorageEntity","{" +
                "\"tasks\":{\"task id5\":{" +
                "\"id\":\"task id5\"," +
                "\"remindRule\":\"WEEKLY_DAYS\"," +
                "\"rulesHours\":[8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23]," +
                "\"rulesWeek\":[1,2,3,4,5,6,7]," +
                "\"rulesDays\":[]," +
                "\"rulesMonths\":[1,2,3,4,5,6,7,8,9,10,11,12]," +
                "\"description\":\"Age is just a number\"," +
                "\"notifiedAt\":[],\"tags\":[]}}," +
                "\"keyValue\":{}}")
        verify(editor).commit()

        verifyNoMoreInteractions(editor)
    }

}