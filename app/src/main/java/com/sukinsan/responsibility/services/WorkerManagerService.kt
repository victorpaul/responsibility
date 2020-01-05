package com.sukinsan.responsibility.services

import android.content.Context
import androidx.work.*
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.utils.StorageUtils
import com.sukinsan.responsibility.workmanagers.ReminderWorker
import java.util.concurrent.TimeUnit

fun newWorkerManagerService(ctx: Context, storageUtils: StorageUtils): WorkerManagerService {
    return WorkerManagerServiceImpl(ctx, storageUtils)
}

interface WorkerManagerService {

    fun runOneOff(task: TaskEntity): Operation

    fun runRecurring(task: TaskEntity): Operation

    fun getRunningWorkersInfo(): String?

}

class WorkerManagerServiceImpl(ctx: Context, val storageUtils: StorageUtils) :
    WorkerManagerService {

    val workManager = WorkManager.getInstance(ctx)

    override fun runOneOff(task: TaskEntity): Operation {
        val remindHelloOnce = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInputData(workDataOf(Pair("taskId", task.id)))
            .setInitialDelay(3, TimeUnit.SECONDS)
            .build()
        return workManager.enqueue(remindHelloOnce)
    }

    override fun runRecurring(task: TaskEntity): Operation {
        val cronJob =
            PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.HOURS)
                .setInputData(workDataOf(Pair("taskId", task.id)))
                .addTag("recurrecntReminder")
                .build()

        task.workerManagerId = cronJob.id.toString()
        storageUtils.save(task)

        return workManager.enqueue(cronJob)
    }

    override fun getRunningWorkersInfo(): String? {
        return workManager.getWorkInfosByTagLiveData("recurrecntReminder")
            .value?.map({ w -> "${w.id} :: ${w.state} :: ${w.runAttemptCount}" })
            ?.joinToString("\n")
    }

}