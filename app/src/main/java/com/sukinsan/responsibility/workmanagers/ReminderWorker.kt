package com.sukinsan.responsibility.workmanagers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sukinsan.responsibility.services.newNotificationService
import com.sukinsan.responsibility.utils.newStorageUtils
import com.sukinsan.responsibility.utils.newTU

class ReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    val LOG_TAG = this.javaClass.toString()

    override fun doWork(): Result { // todo, test it
        val tu = newTU()
        val taskId = inputData.keyValueMap.get("taskId")
        val storage = newStorageUtils(applicationContext, tu)
        val notifySv = newNotificationService(applicationContext, tu, storage)

        val task = storage.getById(taskId as String)

        if (task != null && newTU().getCurrentHour() >= 7 && newTU().getCurrentHour() <= 23) {
            notifySv.registerChannel()
            task?.let {
                notifySv.showNotification(it)
            }

        } else {
            Log.i(LOG_TAG, "Sleep well ${newTU().getCurrentHour()}")
        }
        return Result.success()
    }

}