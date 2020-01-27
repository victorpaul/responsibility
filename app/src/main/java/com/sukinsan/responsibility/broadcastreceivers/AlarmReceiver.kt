package com.sukinsan.responsibility.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sukinsan.responsibility.providers.newSharedPrefDB
import com.sukinsan.responsibility.services.newLogicFlowService
import com.sukinsan.responsibility.services.newNotificationService
import com.sukinsan.responsibility.utils.newTU

class AlarmReceiver : BroadcastReceiver() {

    val LOG_TAG = this.javaClass.toString()

    override fun onReceive(context: Context, intent: Intent) {
        val tu = newTU()
        val dbProvider = newSharedPrefDB(context)
        val notifySv = newNotificationService(context)
        val flowService = newLogicFlowService()

        dbProvider.write { db ->
            return@write db.getTasksMap().map { task ->
                flowService.remindUserAboutTask(
                    task.value,
                    db,
                    tu,
                    notifySv
                ).success
            }.filter { it }.isNotEmpty()
        }
        Log.i(LOG_TAG, "onReceive")
    }
}
