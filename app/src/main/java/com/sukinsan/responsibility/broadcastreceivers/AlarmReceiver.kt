package com.sukinsan.responsibility.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sukinsan.responsibility.providers.newSharedPrefDB
import com.sukinsan.responsibility.services.newNotificationService
import com.sukinsan.responsibility.services.newReminderService

class AlarmReceiver : BroadcastReceiver() {

    val LOG_TAG = this.javaClass.toString()

    override fun onReceive(context: Context, intent: Intent) {
        val dbProvider = newSharedPrefDB(context)
        val workerSv = newReminderService(context, dbProvider)
        val notfService = newNotificationService(context)

        val tasks = dbProvider.read().getTasksList()
        val tasksInterval = 3 / tasks.size

        notfService.registerChannel()
        tasks.forEachIndexed { index, taskEntity ->
            workerSv.runOneTimeWorker(taskEntity, (index * tasksInterval).toLong())
        }
        Log.i(LOG_TAG, "onReceive")
    }
}
