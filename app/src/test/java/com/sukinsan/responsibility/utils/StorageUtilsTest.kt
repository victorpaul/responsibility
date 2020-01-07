package com.sukinsan.responsibility.utils

import android.content.Context
import android.content.SharedPreferences
import com.sukinsan.responsibility.entities.TaskEntity
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class StorageUtilsTest {

    val ctx = mock(Context::class.java)
    val tu = mock(TimeUtils::class.java)
    val sharedPref: SharedPreferences = mock(SharedPreferences::class.java)

    @Before
    fun prepare() {
        doReturn(sharedPref).`when`(ctx).getSharedPreferences(anyString(), anyInt())
    }

    @After
    fun finish() {
        verifyNoMoreInteractions(ctx, tu, sharedPref)
        reset(ctx, tu, sharedPref)
    }

    @Test
    fun success_get_storage_utils() {
        newStorageUtils(ctx, tu)
        verify(ctx).getSharedPreferences("PairTasksIdJson", Context.MODE_PRIVATE)
    }

    @Test
    fun success_save_task() {
        val task = mock(TaskEntity::class.java)
        val editor = mock(SharedPreferences.Editor::class.java)

        doReturn(editor).`when`(sharedPref).edit()
        doReturn("task id").`when`(task).id
        doReturn("task json").`when`(task).toJson()

        val storage = newStorageUtils(ctx, tu)
        storage.save(task)

        verify(ctx).getSharedPreferences("PairTasksIdJson", Context.MODE_PRIVATE)
        verify(sharedPref).edit()
        verify(editor).putString("task id", "task json")
        verify(editor).commit()
    }

    @Test
    fun fail_to_save_invalid_task() {
        fail()
    }

    @Test
    fun success_get_task_by_id() {
        fail()
    }

    @Test
    fun fail_to_get_task_by_id() {
        fail()
    }

    @Test
    fun success_save_last_message_for_task() {
        fail()
    }

    @Test
    fun fail_to_save_last_message_for_invalid_task() {
        fail()
    }

    @Test
    fun success_get_last_message_for_task() {
        fail()
    }

    @Test
    fun fail_to_get_expired_last_message_for_invalid_task() {
        fail()
    }

    @Test
    fun fail_to_get_expired_last_message_for_task() {
        fail()
    }
}