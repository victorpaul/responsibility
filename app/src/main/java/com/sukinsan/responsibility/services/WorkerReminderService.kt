package com.sukinsan.responsibility.services

import android.content.Context
import androidx.work.*
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.utils.StorageUtils
import com.sukinsan.responsibility.workmanagers.ReminderWorker
import java.util.concurrent.TimeUnit

fun newReminderService(ctx: Context, storageUtils: StorageUtils): WorkerReminderService {
    return WorkerManagerServiceImpl(ctx, storageUtils)
}

interface WorkerReminderService {

    fun getWorkManager(): WorkManager

    fun runRecurring(task: TaskEntity): Operation

}

class WorkerManagerServiceImpl(val ctx: Context, val storageUtils: StorageUtils) : WorkerReminderService {

    override fun getWorkManager(): WorkManager {
        return WorkManager.getInstance(ctx)
    }

    override fun runRecurring(task: TaskEntity): Operation {
        val cronJob =
            PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.HOURS)
                .setInputData(workDataOf(Pair("taskId", task.id)))
                .build()
        storageUtils.lock {
            task.workerManagerId = cronJob.id.toString()
            storageUtils.save(task)
        }
        return getWorkManager().enqueueUniquePeriodicWork(task.id, ExistingPeriodicWorkPolicy.REPLACE, cronJob)
    }

}