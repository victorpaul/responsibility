package com.sukinsan.responsibility.services

import android.content.Context
import androidx.work.*
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.providers.DBProvider
import com.sukinsan.responsibility.utils.newTU
import org.junit.After
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import java.util.concurrent.TimeUnit

class WorkerManagerServiceTest {

    val ctx = Mockito.mock(Context::class.java)
    val su = Mockito.mock(DBProvider::class.java)

    @After
    fun finish() {
        Mockito.reset(ctx, su)
    }

    @Test
    fun success_run_recurring_task() {
        val task = TaskEntity(
            "task id", RemindRuleEnum.DAILY, "Drink water",
            newTU(3000, 10, 10, 21, 10, 0).getDate()
        )
        val service = Mockito.spy(newReminderService(ctx, su))
        val wm = Mockito.mock(WorkManager::class.java)
        val workRequestBuilder = Mockito.mock(PeriodicWorkRequest.Builder::class.java)
        val workRequest = Mockito.mock(PeriodicWorkRequest::class.java)
        val operation = Mockito.mock(Operation::class.java)

        doReturn(workRequestBuilder).`when`(service).createPeriodicWorkRequestBuilder(1, TimeUnit.HOURS) // todo would be great to have anyLong, any<TimeUnit>()
        doReturn(workRequestBuilder).`when`(workRequestBuilder).setInputData(any<Data>())
        doReturn(workRequest).`when`(workRequestBuilder).build()
        doReturn(wm).`when`(service).getWorkManager()
        doReturn(operation).`when`(wm).enqueueUniquePeriodicWork(anyString(), any<ExistingPeriodicWorkPolicy>(), any<PeriodicWorkRequest>())

        service.runRecurringWorker(task)

        verify(service).getWorkManager()
        verify(service).createPeriodicWorkRequestBuilder(1, TimeUnit.HOURS)
        verify(workRequestBuilder).setInputData(workDataOf(Pair("taskId", "task id")))
        verify(workRequestBuilder).build()
        verify(wm).enqueueUniquePeriodicWork("task id", ExistingPeriodicWorkPolicy.REPLACE, workRequest)


    }

}