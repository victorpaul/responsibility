package com.sukinsan.responsibility.workmanagers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sukinsan.responsibility.providers.newSharedPrefDB
import com.sukinsan.responsibility.services.newLogicFlowService
import com.sukinsan.responsibility.services.newNotificationService
import com.sukinsan.responsibility.utils.newTU

class ReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    val LOG_TAG = this.javaClass.toString()

    override fun doWork(): Result { // todo, test it
        val tu = newTU()
        val taskId = inputData.keyValueMap.get("taskId")
        val storageUtils = newSharedPrefDB(applicationContext, tu)
        val notifySv = newNotificationService(applicationContext, tu, storageUtils)

        val flowService = newLogicFlowService(tu, notifySv)

        if (flowService.isItNotificationWindow()) {
            notifySv.registerChannel()
            storageUtils.write { db ->
                val task = db.getTaskById(taskId as String)
                val r = flowService.remindUserAboutTask(task, db)
                Log.i(LOG_TAG, r.message)
                return@write r.success
            }
        }
        return Result.success()
    }

}