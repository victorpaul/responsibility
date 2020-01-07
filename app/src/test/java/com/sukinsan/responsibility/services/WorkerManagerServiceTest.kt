package com.sukinsan.responsibility.services

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.Operation
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.utils.StorageUtils
import com.sukinsan.responsibility.utils.newTU
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify

class WorkerManagerServiceTest {

    val ctx = Mockito.mock(Context::class.java)
    val su = Mockito.mock(StorageUtils::class.java)

    @After
    fun finish() {
        Mockito.reset(ctx, su)
    }

    @Test
    fun success_run_recurring_task() {
        val wm = Mockito.mock(WorkManager::class.java)
        val operation = Mockito.mock(Operation::class.java)
        val task = TaskEntity(
            "task id", RemindRuleEnum.DAILY, "Drink water",
            newTU(3000, 10, 10, 21, 10, 0).getDate()
        )
        val service = Mockito.spy(newReminderService(ctx, su))
        doReturn(wm).`when`(service).getWorkManager()
        doReturn(operation).`when`(wm).enqueueUniquePeriodicWork(anyString(), any<ExistingPeriodicWorkPolicy>(), any<PeriodicWorkRequest>())

        service.runRecurring(task)

        verify(service).getWorkManager()
        verify(wm).enqueueUniquePeriodicWork("task id", ExistingPeriodicWorkPolicy.REPLACE, any<PeriodicWorkRequest>())


    }

}