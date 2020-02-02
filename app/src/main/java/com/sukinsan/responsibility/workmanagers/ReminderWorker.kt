package com.sukinsan.responsibility.workmanagers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sukinsan.responsibility.providers.newSharedPrefDB
import com.sukinsan.responsibility.utils.newTaskUtils
import com.sukinsan.responsibility.services.newNotificationService
import com.sukinsan.responsibility.utils.newTU

class ReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result { // todo, test it
        val tu = newTU()
        val taskId = inputData.keyValueMap.get("taskId")
        val storageUtils = newSharedPrefDB(applicationContext)
        val notifySv = newNotificationService(applicationContext)
        val flowService = newTaskUtils()

        storageUtils.write { db ->
            val task = db.getTaskById(taskId as String)
            if (task != null) {
                return@write flowService.remind(task, db, tu, notifySv).success
            }
            return@write false
        }
        
        return Result.success()
    }

}