package com.sukinsan.responsibility.workmanagers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sukinsan.responsibility.services.newNotificationService
import com.sukinsan.responsibility.services.newWorkerManagerService
import com.sukinsan.responsibility.utils.newStorageUtils
import com.sukinsan.responsibility.utils.newTU

class ReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    val LOG_TAG = this.javaClass.toString()

    override fun doWork(): Result { // todo, test it

        val taskId = inputData.keyValueMap.get("taskId")
        val storage = newStorageUtils(applicationContext)
        val wmService = newWorkerManagerService(applicationContext, storage)
        val tu = newTU()
        val notifySv = newNotificationService(applicationContext, tu)

        val task = storage.getById(taskId as String)

        Log.i(LOG_TAG, "Description: ${task.description}")


        if (newTU().getCurrentHour() >= 8 && newTU().getCurrentHour() <= 23) {

            notifySv.registerChannel()
            notifySv.showNotification(task)


        } else {
            Log.i(LOG_TAG, "Sleep well ${newTU().getCurrentHour()}")
        }

        wmService.getRunningWorkersInfo()?.let {
            notifySv.showNotification(
                "Workers info",
                it,
                3
            )
        }

        return Result.success()
    }

}